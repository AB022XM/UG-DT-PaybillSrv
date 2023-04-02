import { IValidateCustomerById, NewValidateCustomerById } from './validate-customer-by-id.model';

export const sampleWithRequiredData: IValidateCustomerById = {
  id: 70714,
  customnerId: 72793,
  billCode: 59369,
};

export const sampleWithPartialData: IValidateCustomerById = {
  id: 49031,
  customnerId: 51773,
  billCode: 40972,
};

export const sampleWithFullData: IValidateCustomerById = {
  id: 33619,
  customnerId: 47167,
  billCode: 10189,
};

export const sampleWithNewData: NewValidateCustomerById = {
  customnerId: 71779,
  billCode: 68233,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
