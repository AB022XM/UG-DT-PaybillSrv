import dayjs from 'dayjs/esm';

import { RecordStatus } from 'app/entities/enumerations/record-status.model';

import { IStudent, NewStudent } from './student.model';

export const sampleWithRequiredData: IStudent = {
  id: 23105,
  recordUniqueIdentifier: 'da4cf75e-65a1-4e59-9051-cda5b83a83c5',
  studentId: 39489,
  studentCode: 44990,
  studentClassId: 58818,
  contactId: 53419,
  addressId: 42579,
  firstName: 'Laney',
  middleName: 'auxiliary Tools Savings',
  lastName: 'Becker',
  paymentCode: 'deposit Pizza',
  dateOfBirth: dayjs('2023-04-02'),
  outStandingAmount: 'GB Money',
  status: RecordStatus['INACTIVE'],
};

export const sampleWithPartialData: IStudent = {
  id: 92546,
  recordUniqueIdentifier: '1e0bfb5c-ec18-4090-83e3-5940cb9c5d1c',
  studentId: 8281,
  studentCode: 92308,
  studentClassId: 69805,
  contactId: 49443,
  addressId: 75966,
  firstName: 'Daija',
  middleName: 'gold hack Silver',
  lastName: 'Wunsch',
  paymentCode: 'invoice Vision-orien',
  dateOfBirth: dayjs('2023-04-02'),
  outStandingAmount: 'driver s',
  status: RecordStatus['INACTIVE'],
  billerAddress: 'e-markets Pants Supervisor',
  freeField2: 'payment SSL',
  freeField3: 'Industrial Village Credit',
};

export const sampleWithFullData: IStudent = {
  id: 21373,
  recordUniqueIdentifier: '17cf9b34-f20c-4343-a8c5-bc3b5499ba4f',
  studentId: 50700,
  studentCode: 69151,
  studentClassId: 56032,
  contactId: 87699,
  addressId: 64783,
  firstName: 'Florencio',
  middleName: 'Factors',
  lastName: 'Spinka',
  paymentCode: 'override',
  dateOfBirth: dayjs('2023-04-02'),
  outStandingAmount: 'black',
  status: RecordStatus['ACTIVE'],
  billerContact: 'mobile azure',
  billerAddress: 'matrix',
  freeField1: 'User-friendly primary',
  freeField2: 'ubiquitous Soft Account',
  freeField3: 'foreground mobile',
  isDeleted: dayjs('2023-04-02'),
};

export const sampleWithNewData: NewStudent = {
  recordUniqueIdentifier: '2cb8d6a1-2156-4a62-801e-d62911d92cc5',
  studentId: 51214,
  studentCode: 33238,
  studentClassId: 32762,
  contactId: 55473,
  addressId: 81583,
  firstName: 'Gregoria',
  middleName: 'Auto Dynamic',
  lastName: 'Spinka',
  paymentCode: 'Granite',
  dateOfBirth: dayjs('2023-04-02'),
  outStandingAmount: 'Rubber',
  status: RecordStatus['ACTIVE'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
