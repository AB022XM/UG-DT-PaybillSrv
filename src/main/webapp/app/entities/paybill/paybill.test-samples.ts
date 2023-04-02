import dayjs from 'dayjs/esm';

import { PaymentChannel } from 'app/entities/enumerations/payment-channel.model';

import { IPaybill, NewPaybill } from './paybill.model';

export const sampleWithRequiredData: IPaybill = {
  id: 74480,
  recordUniqueIdentifier: 'f0d4138d-1a03-4184-b4bf-bd515a40e026',
  feeId: 'SAS Carolina Cambridgeshire',
  firstName: 'Aletha',
  lastName: 'Gerlach',
  middleName: 'high-level Strategist',
  paymentCode: 'Computers Strategist alarm',
  schoolCode: 'Pants Fresh',
  schoolName: 'system-worthy Spring Ports',
  studentClass: 'Switchable',
  paymentChannel: PaymentChannel['POINTOFSALE'],
};

export const sampleWithPartialData: IPaybill = {
  id: 10131,
  recordUniqueIdentifier: 'e84993ba-12d6-40d6-b7aa-14fa794cf88d',
  processTimestamp: dayjs('2023-04-02'),
  feeAmount: 89830,
  feeId: 'web implement',
  firstName: 'Alexie',
  lastName: 'Renner',
  middleName: 'SCSI',
  outstandingAmount: 31801,
  paymentCode: 'Hat compressing',
  schoolCode: 'open-source',
  schoolName: 'Shoes panel',
  studentClass: 'payment copying',
  paymentChannel: PaymentChannel['USSD'],
  freeField1: 'Marketing Metal',
  freeField2: 'ivory',
  freeField3: 'Right-sized Solutions',
};

export const sampleWithFullData: IPaybill = {
  id: 64494,
  recordUniqueIdentifier: 'bf52c8b1-fffa-46ce-80e2-4c342bd94266',
  processTimestamp: dayjs('2023-04-01'),
  feeAmount: 10295,
  feeDescription: 'deposit web white',
  feeDueFromDate: dayjs('2023-04-02'),
  feeDueToDate: dayjs('2023-04-02'),
  feeId: 'Optimized COM Marketing',
  firstName: 'Xander',
  lastName: 'Rolfson',
  middleName: 'leverage',
  outstandingAmount: 54164,
  paymentCode: 'Frozen contingency Cambridgeshire',
  schoolCode: 'Handcrafted olive',
  schoolName: 'Soft bandwidth-monitored Practical',
  studentClass: 'Practical parse Towels',
  paymentChannel: PaymentChannel['USSD'],
  freeField1: 'Zambian Representative payment',
  freeField2: 'calculating Avon',
  freeField3: 'Intelligent Vermont',
};

export const sampleWithNewData: NewPaybill = {
  recordUniqueIdentifier: '03ed809b-9155-4760-b6e3-7e3b24437f30',
  feeId: 'Heights transmitting Kids',
  firstName: 'Alba',
  lastName: 'Ortiz',
  middleName: 'synergy Kentucky',
  paymentCode: 'Rial Generic',
  schoolCode: 'Monaco PCI bypassing',
  schoolName: 'Key',
  studentClass: 'Tenge',
  paymentChannel: PaymentChannel['ABSAINTERNETBANKING'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
