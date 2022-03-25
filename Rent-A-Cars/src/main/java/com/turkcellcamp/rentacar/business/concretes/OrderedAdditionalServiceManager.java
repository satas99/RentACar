package com.turkcellcamp.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.business.abstracts.AdditionalServiceService;
import com.turkcellcamp.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcellcamp.rentacar.business.abstracts.RentalCarService;
import com.turkcellcamp.rentacar.business.constants.BusinessMessages;
import com.turkcellcamp.rentacar.business.dtos.gets.GetAdditionalServiceByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetCustomerByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetOrderedAdditionalServiceByIdDto;
import com.turkcellcamp.rentacar.business.dtos.gets.GetRentalCarByIdDto;
import com.turkcellcamp.rentacar.business.dtos.lists.ListOrderedAdditionalServiceDto;
import com.turkcellcamp.rentacar.business.requests.creates.CreateInvoiceRequest;
import com.turkcellcamp.rentacar.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import com.turkcellcamp.rentacar.business.requests.updates.UpdateOrderedAdditionalServiceRequest;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.dataaccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcellcamp.rentacar.entities.concretes.AdditionalService;
import com.turkcellcamp.rentacar.entities.concretes.Customer;
import com.turkcellcamp.rentacar.entities.concretes.Invoice;
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

		return new SuccessDataResult<List<ListOrderedAdditionalServiceDto>>(response, BusinessMessages.SUCCESS);
	}
	
	@Override
	public Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest){

		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(createOrderedAdditionalServiceRequest, OrderedAdditionalService.class);
		
		orderedAdditionalService.setOrderedAdditionalServiceId(0);
		
		idCorrectionForAdd(orderedAdditionalService,createOrderedAdditionalServiceRequest);//id düzeltme işleminde rentalcar'ın getbyıd metodu üzerinden rentalcarın varlığı kontrol edilmiştir."

		checkIfAdditionalServiceExists(createOrderedAdditionalServiceRequest.getAdditionalServiceId());

		this.orderedAdditionalServiceDao.save(orderedAdditionalService);

		return new SuccessResult(BusinessMessages.ORDEREDADDITIONALSERVICEADDED);
	}

	@Override
	public Result update(int id, UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest){
		
		checkIfOrderedAdditionalServiceExists(id);
		
		checkIfRentalExists(updateOrderedAdditionalServiceRequest.getRentalCarId());
		
		checkIfAdditionalServiceExists(updateOrderedAdditionalServiceRequest.getAdditionalServiceId());
		
		OrderedAdditionalService orderedadditionalService = this.orderedAdditionalServiceDao.getByOrderedAdditionalServiceId(id);
		updateOperation(orderedadditionalService,updateOrderedAdditionalServiceRequest);
		
		this.orderedAdditionalServiceDao.save(orderedadditionalService);
		
		return new SuccessResult(BusinessMessages.ORDEREDADDITIONALSERVICEUPDATED);
	}

	@Override
	public Result delete(int id){
		
		checkIfOrderedAdditionalServiceExists(id);

		this.orderedAdditionalServiceDao.deleteById(id);

		return new SuccessResult(BusinessMessages.ORDEREDADDITIONALSERVICEDELETED);
	}

	@Override
	public DataResult<GetOrderedAdditionalServiceByIdDto> getById(int orderedadditionalServiceId){
			
		OrderedAdditionalService result = checkIfOrderedAdditionalServiceExists(orderedadditionalServiceId);

		GetOrderedAdditionalServiceByIdDto response = this.modelMapperService.forDto().map(result, GetOrderedAdditionalServiceByIdDto.class);
			
		return new SuccessDataResult<GetOrderedAdditionalServiceByIdDto>(response, BusinessMessages.SUCCESS);

	}
	
	@Override
	public DataResult<List<ListOrderedAdditionalServiceDto>> getOrderedAdditionalServiceByRentalCarId(int rentalCarId) {
		
		List<OrderedAdditionalService> result = this.orderedAdditionalServiceDao.getByRentalCar_rentalCarId(rentalCarId);
		List<ListOrderedAdditionalServiceDto> response = result.stream().map(orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService, ListOrderedAdditionalServiceDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<ListOrderedAdditionalServiceDto>>(response, BusinessMessages.SUCCESS);
	}
	
	private RentalCar checkIfRentalExists(int id){
		
		GetRentalCarByIdDto getRentalCarByIdDto = this.rentalCarService.getById(id).getData();
		
		if (getRentalCarByIdDto == null) {
			throw new BusinessException(BusinessMessages.RENTALCARNOTFOUND);
		}
		RentalCar rentalCar = this.modelMapperService.forDto().map(getRentalCarByIdDto, RentalCar.class);
		return rentalCar;
	}
	
	private void idCorrectionForAdd(OrderedAdditionalService orderedAdditionalService, CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) {

		GetRentalCarByIdDto getRentalCarByIdDto = this.rentalCarService.getById(orderedAdditionalService.getRentalCar().getRentalCarId()).getData();
		RentalCar rentalCar = this.modelMapperService.forDto().map(getRentalCarByIdDto, RentalCar.class);

		orderedAdditionalService.setRentalCar(rentalCar);
	}

	private AdditionalService checkIfAdditionalServiceExists(int id){
		
		GetAdditionalServiceByIdDto getAdditionalServiceByIdDto = this.additionalServiceService.getByAdditionalServiceId(id).getData();
		
		if (getAdditionalServiceByIdDto == null) {
			throw new BusinessException(BusinessMessages.ADDITIONALSERVICENOTFOUND);
		}
		AdditionalService additionalService = this.modelMapperService.forDto().map(getAdditionalServiceByIdDto, AdditionalService.class);
		return additionalService;
	}
	
	private OrderedAdditionalService checkIfOrderedAdditionalServiceExists(int id){
		
		OrderedAdditionalService orderedAdditionalService =this.orderedAdditionalServiceDao.getByOrderedAdditionalServiceId(id);
		
		if(orderedAdditionalService==null) {
			throw new BusinessException(BusinessMessages.ORDEREDADDITIONALSERVICENOTFOUND);
		}
		return orderedAdditionalService;
	}
	
	private void updateOperation(OrderedAdditionalService orderedAdditionalService, UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest){
		
		AdditionalService additionalService = checkIfAdditionalServiceExists(updateOrderedAdditionalServiceRequest.getAdditionalServiceId());
		orderedAdditionalService.setAdditionalService(additionalService);
		
		RentalCar rentalCar = checkIfRentalExists(updateOrderedAdditionalServiceRequest.getRentalCarId());
		orderedAdditionalService.setRentalCar(rentalCar);

		
	}


}
