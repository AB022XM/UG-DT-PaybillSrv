import dayjs from 'dayjs/esm';

import { RecordStatus } from 'app/entities/enumerations/record-status.model';

import { ICustomer, NewCustomer } from './customer.model';

export const sampleWithRequiredData: ICustomer = {
  id: 24379,
  recordUniqueIdentifier: '5f845f9e-714c-4c88-9e8d-9501afe516ea',
  customerId: 'Estonia input withdrawal',
  status: RecordStatus['INACTIVE'],
};

export const sampleWithPartialData: ICustomer = {
  id: 56786,
  recordUniqueIdentifier: 'a4595673-fdbd-4321-9180-2cf86cacd0db',
  addressId: 99519,
  customerId: 'Investor clicks-and-mortar',
  middleName: 'back-end Benin capacitor',
  lastName: 'Haag',
  dateOfBirth: dayjs('2023-04-02'),
  status: RecordStatus['ACTIVE'],
  customerContactId: 'Steel Lodge',
  freeField1: 'Licensed',
};

export const sampleWithFullData: ICustomer = {
  id: 60280,
  recordUniqueIdentifier: 'f255a731-a8c1-4c1c-b1f7-c1fab987238c',
  contactId: 88552,
  addressId: 83996,
  customerId: 'Avon',
  firstName: 'Cynthia',
  middleName: 'transmitter',
  lastName: 'Rippin',
  dateOfBirth: dayjs('2023-04-02'),
  status: RecordStatus['INACTIVE'],
  customerAddressId: 'up',
  customerContactId: 'ADP',
  freeField1: 'contingency lime Music',
  freeField2: 'application PCI indexing',
  freeField3: 'Automotive',
};

export const sampleWithNewData: NewCustomer = {
  recordUniqueIdentifier: 'fa2244a6-d906-4082-884e-a3a810efb638',
  customerId: 'Incredible Dinar',
  status: RecordStatus['INACTIVE'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
