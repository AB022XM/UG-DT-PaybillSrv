import { RecordStatus } from 'app/entities/enumerations/record-status.model';

import { IContactInfo, NewContactInfo } from './contact-info.model';

export const sampleWithRequiredData: IContactInfo = {
  id: 8174,
  recordUniqueIdentifier: '6f8e4048-fa52-48e3-8f48-ad9b844c0763',
};

export const sampleWithPartialData: IContactInfo = {
  id: 93727,
  recordUniqueIdentifier: '78f8415b-0600-434e-8592-642c60f8c5ad',
  contactId: 'core',
  emailAddress: 'web-readiness circuit leverage',
  isDeleted: true,
  freeField2: 'Steel',
};

export const sampleWithFullData: IContactInfo = {
  id: 5242,
  recordUniqueIdentifier: '5d3acd4e-2bbd-4e18-b528-591e7f819dba',
  contactId: 'Borders',
  phoneNumber: 'base deposit',
  emailAddress: 'THX port',
  parentsPhoneNumber: 'District multi-state Computers',
  status: RecordStatus['INACTIVE'],
  isDeleted: false,
  freeField1: 'GB Customer',
  freeField2: 'Metrics',
  freeField3: 'Concrete installation Fuerte',
};

export const sampleWithNewData: NewContactInfo = {
  recordUniqueIdentifier: '59b180c4-bfe9-41fd-a9c9-bf37b9e1bcf1',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
