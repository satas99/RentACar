package com.turkcellcamp.rentacar.business.concretes;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.internal.bytebuddy.dynamic.scaffold.MethodRegistry.Handler.ForAbstractMethod;
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
import com.turkcellcamp.rentacar.business.dtos.lists.ListRentalCarDto;
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
		List<ListOrderedAdditionalServiceDto> response = result.stream().map(orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService,ListOrderedAdditionalServiceDto.class)).collect(Collectors.toList());
		
		idCorrectionForGetAll(result,response);
		
		return new SuccessDataResult<List<ListOrderedAdditionalServiceDto>>(response, BusinessMessages.SUCCESS);
	}
	
	@Override
	public void add(List<Integer> additionalServiceIds, int rentalCarId ){
		
		for (Integer id : additionalServiceIds) {
			checkIfAdditionalServiceExists(id);
		}
		
		checkIfRentalExists(rentalCarId);
		
		for (int additionalService : additionalServiceIds) {
			
			CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest = new CreateOrderedAdditionalServiceRequest();
			createOrderedAdditionalServiceRequest.setAdditionalServiceId(additionalService);
			createOrderedAdditionalServiceRequest.setRentalCarId(rentalCarId);
			
			OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forDto().map(createOrderedAdditionalServiceRequest, OrderedAdditionalService.class);
			orderedAdditionalService.setOrderedAdditionalServiceId(0);		

			this.orderedAdditionalServiceDao.save(orderedAdditionalService);
		}

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
		
		idCorrectionForGetById(result,response);
			
		return new SuccessDataResult<GetOrderedAdditionalServiceByIdDto>(response, BusinessMessages.SUCCESS);

	}
	
	@Override
	public DataResult<List<ListOrderedAdditionalServiceDto>> getOrderedAdditionalServiceByRentalCarId(int rentalCarId) {
		
		List<OrderedAdditionalService> result = this.orderedAdditionalServiceDao.getByRentalCar_rentalCarId(rentalCarId);
		List<ListOrderedAdditionalServiceDto> response = result.stream().map(orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService, ListOrderedAdditionalServiceDto.class)).collect(Collectors.toList());

		idCorrectionForGetAll(result,response);
		
		return new SuccessDataResult<List<ListOrderedAdditionalServiceDto>>(response, BusinessMessages.SUCCESS);
	}
	
	private void idCorrectionForGetById(OrderedAdditionalService result, GetOrderedAdditionalServiceByIdDto response) {
	
		response.setRentalCarId(result.getRentalCar().getRentalCarId());	
	}

	private List<ListOrderedAdditionalServiceDto> idCorrectionForGetAll(List<OrderedAdditionalService> result, List<ListOrderedAdditionalServiceDto> response) {

		for (int i = 0; i < result.size(); i++) {
			response.get(i).setRentalCarId(result.get(i).getRentalCar().getRentalCarId());
		}

		return response;

	}
	private RentalCar checkIfRentalExists(int id){
		
		GetRentalCarByIdDto getRentalCarByIdDto = this.rentalCarService.getById(id).getData();
		
		RentalCar rentalCar = this.modelMapperService.forDto().map(getRentalCarByIdDto, RentalCar.class);
		return rentalCar;
	}

	private AdditionalService checkIfAdditionalServiceExists(int id){
		
		GetAdditionalServiceByIdDto getAdditionalServiceByIdDto = this.additionalServiceService.getById(id).getData();
		
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
