import dayjs from 'dayjs/esm';

import { IAddress, NewAddress } from './address.model';

export const sampleWithRequiredData: IAddress = {
  id: 88754,
  recordUniqueIdentifier: 'bb15bca8-2ee7-41fd-be57-59198cd69703',
  addressId: 58936,
  addresssName: 'Exclusive Forward',
};

export const sampleWithPartialData: IAddress = {
  id: 20928,
  recordUniqueIdentifier: '471e71a2-0d55-4d29-adc1-58f2c2ec55da',
  addressId: 24986,
  addresssName: 'experiences Re-contextualized',
};

export const sampleWithFullData: IAddress = {
  id: 79767,
  recordUniqueIdentifier: '31b34869-da58-4b7a-9c8a-75c4fc5ea6e5',
  addressId: 2622,
  addresssName: 'Gloves',
  addressDescription: 'Salad throughput Dollar',
  createdAt: dayjs('2023-04-02'),
  updatedAt: dayjs('2023-04-02'),
  isDeleted: dayjs('2023-04-02'),
};

export const sampleWithNewData: NewAddress = {
  recordUniqueIdentifier: '23c30b2c-e541-471d-a4b0-704e41be7713',
  addressId: 65427,
  addresssName: 'CSS modular',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
