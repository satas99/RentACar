package com.turkcellcamp.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcellcamp.rentacar.business.abstracts.AdditionalServiceService;
import com.turkcellcamp.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcellcamp.rentacar.business.abstracts.RentalCarService;
import com.turkcellcamp.rentacar.business.dtos.gets.GetAdditionalServiceByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetOrderedAdditionalServiceByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetRentalCarByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListOrderedAdditionalServiceDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateOrderedAdditionalServiceRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.ErrorDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.dataaccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcellcamp.rentacar.entities.concretes.AdditionalService;
import com.turkcellcamp.rentacar.entities.concretes.OrderedAdditionalService;
import com.turkcellcamp.rentacar.entities.concretes.RentalCar;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService {
	private OrderedAdditionalServiceDao orderedAdditionalServiceDao;
	private ModelMapperService modelMapperService;
	private RentalCarService rentalCarService;
	private AdditionalServiceService additionalServiceService;

	@Autowired
	public OrderedAdditionalServiceManager(OrderedAdditionalServiceDao orderedAdditionalServiceDao,ModelMapperService modelMapperService, RentalCarService rentalCarService,AdditionalServiceService additionalServiceService) {

		this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
		this.modelMapperService = modelMapperService;
		this.rentalCarService = rentalCarService;
		this.additionalServiceService = additionalServiceService;
	}

	@Override
	public DataResult<List<ListOrderedAdditionalServiceDto>> getAll() {

		List<OrderedAdditionalService> result = this.orderedAdditionalServiceDao.findAll();
		List<ListOrderedAdditionalServiceDto> response = result.stream()
				.map(orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService,
						ListOrderedAdditionalServiceDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListOrderedAdditionalServiceDto>>(response, "Success");
	}
	
	@Transactional
	@Override
	public Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest){

		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(createOrderedAdditionalServiceRequest, OrderedAdditionalService.class);
		
		orderedAdditionalService.setOrderedAdditionalServiceId(0);
		
		RentalCar rentalCar = checkIfRentalExists(createOrderedAdditionalServiceRequest.getRentalCarId());
		orderedAdditionalService.setRentalCar(rentalCar);

		checkIfAdditionalServiceExists(createOrderedAdditionalServiceRequest.getAdditionalServiceId());

		this.orderedAdditionalServiceDao.save(orderedAdditionalService);

		return new SuccessResult("OrderedAdditionalService.Added");
	}

	@Override
	public Result delete(int id){
		
		checkIfOrderedAdditionalServiceExists(id);

		this.orderedAdditionalServiceDao.deleteById(id);

		return new SuccessResult(" OrderedAdditionalService.Deleted");
	}
	
	@Override
	public Result update(int id, UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest){
		
		checkIfOrderedAdditionalServiceExists(id);
		
		checkIfRentalExists(updateOrderedAdditionalServiceRequest.getRentalCarId());
		
		checkIfAdditionalServiceExists(updateOrderedAdditionalServiceRequest.getAdditionalServiceId());
		
		OrderedAdditionalService orderedadditionalService = this.orderedAdditionalServiceDao.getByOrderedAdditionalServiceId(id);
		updateOperation(orderedadditionalService,updateOrderedAdditionalServiceRequest);
		
		this.orderedAdditionalServiceDao.save(orderedadditionalService);
		
		return new SuccessResult(" OrderedAdditionalService.Updated");
	}

	@Override
	public DataResult<GetOrderedAdditionalServiceByIdDto> getByOrderedAdditionalServiceId(int orderedadditionalServiceId){
			
		OrderedAdditionalService result = this.orderedAdditionalServiceDao.getByOrderedAdditionalServiceId(orderedadditionalServiceId);
	
		if (result!=null){
			GetOrderedAdditionalServiceByIdDto response = this.modelMapperService.forDto().map(result, GetOrderedAdditionalServiceByIdDto.class);
			
			return new SuccessDataResult<GetOrderedAdditionalServiceByIdDto>(response, "Success");
			
		}
		return new ErrorDataResult<GetOrderedAdditionalServiceByIdDto>("Cannot find an ordered additional service with this Id.");
	}
	
	@Override
	public DataResult<List<ListOrderedAdditionalServiceDto>> getOrderedAdditionalServiceByRentalCarId(int rentalCarId) {
		
		List<OrderedAdditionalService> result = this.orderedAdditionalServiceDao.getByRentalCar_rentalCarId(rentalCarId);
		List<ListOrderedAdditionalServiceDto> response = result.stream().map(orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService, ListOrderedAdditionalServiceDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<ListOrderedAdditionalServiceDto>>(response, "Success");
	}
	
	private RentalCar checkIfRentalExists(int id){
		
		GetRentalCarByIdDto getRentalCarByIdDto = this.rentalCarService.getById(id).getData();
		
		if (getRentalCarByIdDto == null) {
			throw new BusinessException("Cannot find a rented with this Id.");
		}
		RentalCar rentalCar = this.modelMapperService.forDto().map(getRentalCarByIdDto, RentalCar.class);
		return rentalCar;
	}

	private AdditionalService checkIfAdditionalServiceExists(int id){
		
		GetAdditionalServiceByIdDto getAdditionalServiceByIdDto = this.additionalServiceService.getByAdditionalServiceId(id).getData();
		
		if (getAdditionalServiceByIdDto == null) {
			throw new BusinessException("Cannot find an additional service with this Id.");
		}
		AdditionalService additionalService = this.modelMapperService.forDto().map(getAdditionalServiceByIdDto, AdditionalService.class);
		return additionalService;
	}
	
	private boolean checkIfOrderedAdditionalServiceExists(int id){
		
		if(this.orderedAdditionalServiceDao.getByOrderedAdditionalServiceId(id)!=null) {
			return true;
		}
		throw new BusinessException("Cannot find an ordered additional service with this Id.");
	}
	
	private void updateOperation(OrderedAdditionalService orderedAdditionalService, UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest){
		
		AdditionalService additionalService = checkIfAdditionalServiceExists(updateOrderedAdditionalServiceRequest.getAdditionalServiceId());
		orderedAdditionalService.setAdditionalService(additionalService);
		
		RentalCar rentalCar = checkIfRentalExists(updateOrderedAdditionalServiceRequest.getRentalCarId());
		orderedAdditionalService.setRentalCar(rentalCar);

		
	}


}
