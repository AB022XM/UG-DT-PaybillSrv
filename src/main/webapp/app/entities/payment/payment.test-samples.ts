import { RecordStatus } from 'app/entities/enumerations/record-status.model';
import { PaymentChannel } from 'app/entities/enumerations/payment-channel.model';

import { IPayment, NewPayment } from './payment.model';

export const sampleWithRequiredData: IPayment = {
  id: 47537,
  recordUniqueIdentifier: 'bb75e764-58a7-4ee9-bc22-72eb2852f49a',
  paymentId: 69454,
  paymentCode: 67198,
  fixedAmount: 'generating',
  paymentName: 'Account teal',
  paymentChannel: PaymentChannel['USSD'],
};

export const sampleWithPartialData: IPayment = {
  id: 60404,
  recordUniqueIdentifier: 'ce2dc746-aa30-4f95-82c2-6408ac4100ba',
  paymentId: 29793,
  paymentCode: 81209,
  customerId: 'View',
  feeAmount: 74272,
  feeDescription: 'Synergized Solomon',
  fixedAmount: 'National',
  paymentName: 'withdrawal Cambridgeshire',
  paymentChannel: PaymentChannel['ABSAINTERNETBANKING'],
  freeField1: 'mindshare Synergized monetize',
};

export const sampleWithFullData: IPayment = {
  id: 68023,
  recordUniqueIdentifier: '7a5b3e6c-d539-4594-a345-0cf43940261b',
  paymentId: 65624,
  paymentCode: 39621,
  customerId: 'experiences Rubber Pants',
  feeAmount: 45655,
  isAmountFixed: RecordStatus['ACTIVE'],
  feeDescription: 'Unbranded',
  fixedAmount: 'Bedfordshire deposit engineer',
  paymentName: 'Salad',
  outstandingAmount: 73576,
  paymentChannel: PaymentChannel['POINTOFSALE'],
  freeField1: 'orange web-enabled Cambridgeshire',
  freeField2: 'synergies Borders Caledonia',
  freeField3: 'Hawaii fuchsia',
};

export const sampleWithNewData: NewPayment = {
  recordUniqueIdentifier: '8d8dca22-bc41-477c-8d0b-358d7dabeb79',
  paymentId: 41862,
  paymentCode: 76454,
  fixedAmount: 'Assistant',
  paymentName: 'Account methodologies',
  paymentChannel: PaymentChannel['POINTOFSALE'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
