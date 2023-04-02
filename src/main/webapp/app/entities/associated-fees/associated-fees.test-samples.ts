import dayjs from 'dayjs/esm';

import { IAssociatedFees, NewAssociatedFees } from './associated-fees.model';

export const sampleWithRequiredData: IAssociatedFees = {
  id: 36667,
  recordUniqueIdentifier: '55578892-c9c0-4991-a8bf-88f4953cb749',
  feeId: 4359,
  feeCode: 'Clothing panel mindshare',
  status: false,
};

export const sampleWithPartialData: IAssociatedFees = {
  id: 87447,
  recordUniqueIdentifier: 'dc5ee08c-1f48-4bbf-97cb-f0f0799090b5',
  feeId: 18771,
  feeCode: 'View Product Movies',
  status: true,
  freeField2: 'Lesotho ADP application',
  freeField3: 'Bedfordshire Car Georgia',
  updatedAt: dayjs('2023-04-01'),
  isDeleted: dayjs('2023-04-02'),
};

export const sampleWithFullData: IAssociatedFees = {
  id: 14495,
  recordUniqueIdentifier: '473e49d4-3d87-4f61-a600-fe552c5ea2da',
  feeId: 36557,
  feeCode: 'Sausages Cambridgeshire',
  feeDescription: 'Greece',
  status: false,
  freeField1: 'Liaison cutting-edge Assurance',
  freeField2: 'programming',
  freeField3: 'circuit Garden invoice',
  createdAt: dayjs('2023-04-01'),
  updatedAt: dayjs('2023-04-02'),
  isDeleted: dayjs('2023-04-02'),
};

export const sampleWithNewData: NewAssociatedFees = {
  recordUniqueIdentifier: '339e79d3-c8c9-4e3a-8181-fe9534d0c617',
  feeId: 94514,
  feeCode: 'Money Passage',
  status: true,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
