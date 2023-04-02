import { RecordStatus } from 'app/entities/enumerations/record-status.model';

import { IBillerCatergory, NewBillerCatergory } from './biller-catergory.model';

export const sampleWithRequiredData: IBillerCatergory = {
  id: 4136,
  recordUniqueIdentifier: 'd3e4df6e-9436-465f-b421-0c6c95b97ce5',
  categoryCode: 'matrix service-desk Arkansas',
  categoryName: 'Soft Bedfordshire',
  categoryDescription: 'benchmark dynamic',
  status: RecordStatus['INACTIVE'],
};

export const sampleWithPartialData: IBillerCatergory = {
  id: 37789,
  recordUniqueIdentifier: '4469ba45-b11d-48df-b9f7-c2b2255f6d3b',
  categoryCode: 'Canada Assimilated Personal',
  categoryName: 'content',
  categoryDescription: 'collaborative',
  status: RecordStatus['ACTIVE'],
  freeField1: 'Rial Forge',
};

export const sampleWithFullData: IBillerCatergory = {
  id: 18869,
  recordUniqueIdentifier: 'd7c508ef-5a33-4d27-bc7f-d79659ee1f7c',
  categoryId: 97373,
  categoryCode: 'invoice',
  categoryName: 'Robust Wall',
  categoryDescription: 'Card navigating',
  status: RecordStatus['ACTIVE'],
  freeField1: 'engineer Metal compressing',
  freeField2: 'interactive',
  freeField3: 'USB reintermediate Granite',
  isDeleted: false,
};

export const sampleWithNewData: NewBillerCatergory = {
  recordUniqueIdentifier: 'acea1e41-5bc7-4019-8d8e-bda4dee48a70',
  categoryCode: 'Dalasi Hat',
  categoryName: 'invoice Corporate',
  categoryDescription: 'maximized ROI generation',
  status: RecordStatus['INACTIVE'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
