import { PaymentChannel } from 'app/entities/enumerations/payment-channel.model';
import { RecordStatus } from 'app/entities/enumerations/record-status.model';

import { IPaymentChannel, NewPaymentChannel } from './payment-channel.model';

export const sampleWithRequiredData: IPaymentChannel = {
  id: 20891,
  recordUniqueIdentifier: '4b610604-3cff-47a9-815a-1398e7e2a0c1',
  channelId: 76537,
  channelName: PaymentChannel['ABSAINTERNETBANKING'],
};

export const sampleWithPartialData: IPaymentChannel = {
  id: 72868,
  recordUniqueIdentifier: '78fd0e8c-78e1-41ea-b074-dc1951e5375b',
  channelId: 54344,
  channelName: PaymentChannel['ABSAINTERNETBANKING'],
  freeField1: 'Planner',
};

export const sampleWithFullData: IPaymentChannel = {
  id: 43495,
  recordUniqueIdentifier: 'ea961514-c1b0-481d-8cc5-e0949328a692',
  channelId: 94500,
  channelCode: 50550,
  channelName: PaymentChannel['OVERTHECOUNTER'],
  status: RecordStatus['ACTIVE'],
  freeField1: 'Functionality Way',
  freeField2: 'workforce',
  freeField3: 'Gorgeous innovate',
  isDeleted: RecordStatus['ACTIVE'],
};

export const sampleWithNewData: NewPaymentChannel = {
  recordUniqueIdentifier: '26eba3dd-a6f2-42d6-b420-1b636be9ae55',
  channelId: 71281,
  channelName: PaymentChannel['USSD'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
