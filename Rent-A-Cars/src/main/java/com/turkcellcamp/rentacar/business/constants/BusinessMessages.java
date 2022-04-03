package com.turkcellcamp.rentacar.business.constants;

public class BusinessMessages {
	
	public static final String VALIDATIONERRORS = "Validation.Errors";
	public static final String BUSINESSEXCEPTIONERRORS = "BusinessException.Errors";
	public static final String SUCCESS = "Success";
	public static final String MAİLEXİSTS = "Such a mail exists";
	public static final String TAXNUMBEREXİSTS = "Such a tax number exists.";
	public static final String IDENTITYNUMBEREXİSTS = "Such a identity number exists.";

	public static final String ADDITIONALSERVICEADDED = "AdditionalService.Added";
	public static final String ADDITIONALSERVICEUPDATED = "AdditionalService.Updated";
	public static final String ADDITIONALSERVICEDELETED = "AdditonalService.Deleted";
	public static final String ADDITIONALSERVICENOTFOUND = "Cannot find an additional service with this Id";

	public static final String BRANDADDED = "Brand.Added";
	public static final String BRANDUPDATED = "Brand.Updated";
	public static final String BRANDDELETED = "Brand.Deleted";
	public static final String BRANDNOTFOUND = "Cannot find a brand with this Id";
	public static final String BRANDEXISTS = "Such a brand exists";
	
	public static final String CARACCİDENTADDED = "CarAccident.Added";
	public static final String CARACCİDENTUPDATED = "CarAccident.Updated";
	public static final String CARACCİDENTDELETED = "CarAccident.Deleted";
	public static final String CARACCİDENTNOTFOUND = "Cannot find a car accident with this Id";

	public static final String CARMAINTENANCEADDED = "CarMaintenance.Added";
	public static final String CARMAINTENANCEUPDATED = "CarMaintenance.Updated";
	public static final String CARMAINTENANCEDELETED = "CarMaintenance.Deleted";
	public static final String CARMAINTENANCENOTFOUND = "Cannot find a car maintenance with this Id";
	public static final String CARMAINTENANCECARINRENT = "The car cannot be sent for maintenance because it is on rent";
	public static final String CARMAINTENANCESTILLMAINTENANCED = "The car is still in maintenance";

	public static final String CARADDED = "Car.Added";
	public static final String CARUPDATED = "Car.Updated";
	public static final String CARDELETED = "Car.Deleted";
	public static final String CARNOTFOUND = "Cannot find a car with this Id";

	public static final String COLORADDED = "Color.Added";
	public static final String COLORUPDATED = "Color.Updated";
	public static final String COLORDELETED = "Color.Deleted";
	public static final String COLOREXISTS = "Such a color exists";
	public static final String COLORNOTFOUND = "Cannot find a color with this Id";

	public static final String CORPORATECUSTOMERADDED = "CorporateCustomer.Added";
	public static final String CORPORATECUSTOMERUPDATED = "CorporateCustomer.Updated";
	public static final String CORPORATECUSTOMERDELETED = "CorporateCustomer.Deleted";
	public static final String CORPORATECUSTOMERNOTFOUND = "Cannot find a corporate customer with this Id";
	
	public static final String CREDİTCARDADDED = "CreditCard.Added";
	public static final String CREDİTCARDEXİSTS = "Such a credit card exists";

	public static final String INDIVIDUALCUSTOMERADDED = "IndividualCustomer.Added";
	public static final String INDIVIDUALCUSTOMERUPDATED = "IndividualCustomer.Updated";
	public static final String INDIVIDUALCUSTOMERDELETED = "IndividualCustomer.Deleted";
	public static final String INDIVIDUALCUSTOMERNOTFOUND = "Cannot find a individual customer with this Id";

	public static final String CUSTOMERNOTFOUND = "Cannot find a customer with this Id";

	public static final String INVOICEADDED = "Invoice.Added";
	public static final String INVOICEUPDATED = "Invoice.Updated";
	public static final String INVOICEDELETED = "Invoice.Deleted";
	public static final String INVOICENOTFOUND = "Cannot find a invoice with this Id";
	public static final String INVOICENOEXISTS = "Such an invoice number exists";
	public static final String RENTALCARANINVOICED = "There is an invoice for car rental.";
	
	public static final String ORDEREDADDITIONALSERVICEADDED = "OrderedAdditionalService.Added";
	public static final String ORDEREDADDITIONALSERVICEUPDATED = "OrderedAdditionalService.Updated";
	public static final String ORDEREDADDITIONALSERVICEDELETED = "OrderedAdditonalService.Deleted";
	public static final String ORDEREDADDITIONALSERVICENOTFOUND = "Cannot find an ordered additional service with this Id";
	
	public static final String PAYMENTADDED = "Payment.Added";
	public static final String PAYMENTDELETED = "Payment.Deleted";
	public static final String PAYMENTNOTFOUND = "Cannot find a payment with this Id";
	public static final String INVOICEANPAYMENTED = "There is an invoice for payment";

	public static final String RENTALCARADDED = "RentalCar.Added";
	public static final String RENTALCARUPDATED = "RentalCar.Updated";
	public static final String RENTALCARDELETED = "RentalCar.Deleted";
	public static final String RENTALCARNOTFOUND = "Cannot find a rental with this Id";
	public static final String RENTALCARINMAINTENANCE = "The car cannot be sent for rent because it is on maintenance";
	public static final String RENTALCARINRENT = "The car is still in rental";
	public static final String RENTALCARISRENTED = "The rented car with this id does not exist";
	public static final String RENTALCARFAİLRETURNDATE = "The new return date cannot be before the old return date";
	public static final String RENTALCARRENTDATEFAİLRETURNDATE = "Return date cannot be earlier than rental date";
	

	public static final String USERADDED = "User.Added";
	public static final String USERUPDATED = "User.Updated";
	public static final String USERDELETED = "User.Deleted";
	public static final String USERNOTFOUND = "Cannot find a user with this Id";
}
