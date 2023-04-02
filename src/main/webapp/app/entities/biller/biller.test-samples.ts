import dayjs from 'dayjs/esm';

import { RecordStatus } from 'app/entities/enumerations/record-status.model';

import { IBiller, NewBiller } from './biller.model';

export const sampleWithRequiredData: IBiller = {
  id: 29087,
  recordUniqueIdentifier: '6de71fb6-8ee4-4bb8-89cd-9788256884ff',
  billerCode: 1925,
  categoryId: 29575,
  contactId: 41169,
  addressId: 48576,
  firstName: 'Kellie',
  middleName: 'Avon',
  lastName: 'Runte',
  dateOfBirth: dayjs('2023-04-02'),
  outStandingAmount: 'Berkshir',
  status: RecordStatus['INACTIVE'],
};

export const sampleWithPartialData: IBiller = {
  id: 49812,
  recordUniqueIdentifier: 'c90b242c-44a4-42d5-b9f5-d3118afd5d25',
  billerCode: 36546,
  categoryId: 1404,
  contactId: 7088,
  addressId: 38128,
  firstName: 'Tate',
  middleName: 'Cheese Spain',
  lastName: 'Keeling',
  dateOfBirth: dayjs('2023-04-02'),
  outStandingAmount: 'Outdoors',
  status: RecordStatus['INACTIVE'],
  freeField1: 'circuit',
  freeField3: 'Tactics interface Metal',
};

export const sampleWithFullData: IBiller = {
  id: 83828,
  recordUniqueIdentifier: 'a91216ae-8f48-446d-9753-cbae9adab0d6',
  billerId: 77691,
  billerCode: 36282,
  categoryId: 71758,
  contactId: 8176,
  addressId: 65467,
  firstName: 'Katlynn',
  middleName: 'Austria Georgia',
  lastName: 'Rosenbaum',
  dateOfBirth: dayjs('2023-04-02'),
  outStandingAmount: 'budgetar',
  status: RecordStatus['INACTIVE'],
  freeField1: 'deposit Regional orchid',
  freeField2: 'Iowa Ukraine Gloves',
  freeField3: 'Steel',
  isDeleted: dayjs('2023-04-02'),
};

export const sampleWithNewData: NewBiller = {
  recordUniqueIdentifier: '1e936424-de4f-41a6-b0cc-e8f214cd2480',
  billerCode: 79851,
  categoryId: 52737,
  contactId: 33011,
  addressId: 59726,
  firstName: 'Elyssa',
  middleName: 'Sausages',
  lastName: 'Turner',
  dateOfBirth: dayjs('2023-04-02'),
  outStandingAmount: 'Tactics ',
  status: RecordStatus['INACTIVE'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
