import dayjs from 'dayjs/esm';

import { RecordStatus } from 'app/entities/enumerations/record-status.model';

import { ISchool, NewSchool } from './school.model';

export const sampleWithRequiredData: ISchool = {
  id: 82208,
  recordUniqueIdentifier: 'f8185c2d-858f-44c9-a3e7-fb81dd39adb2',
  schoolId: 72443,
  schoolCode: 'Rubber maroon',
  schoolName: 'Sausages',
  status: RecordStatus['ACTIVE'],
};

export const sampleWithPartialData: ISchool = {
  id: 47266,
  recordUniqueIdentifier: 'ae906b19-48c5-462f-96d8-cde3506e6d0d',
  schoolId: 5732,
  schoolCode: 'Group application up',
  schoolPhoneNumber: 'Integration Soft Rubber',
  schoolemailAddess: 'Soap Mississippi',
  schoolName: 'Exclusive Configuration',
  status: RecordStatus['ACTIVE'],
  freeField1: 'Bike turquoise grow',
  registrationDate: dayjs('2023-04-02T16:05'),
  freeField3: 'Human',
  isDeleted: dayjs('2023-04-02'),
};

export const sampleWithFullData: ISchool = {
  id: 7835,
  recordUniqueIdentifier: 'f172055b-bb67-4156-8b30-37ca72f18f34',
  schoolId: 69717,
  schoolCode: '5th partnerships Sports',
  schoolPhoneNumber: 'withdrawal Configuration Lucia',
  schoolAlternativePhoneNumber: '1080p digital Berkshire',
  schoolemailAddess: 'SCSI mesh Puerto',
  schoolName: 'Fresh Response',
  status: RecordStatus['ACTIVE'],
  freeField1: 'Auto schemas',
  registrationDate: dayjs('2023-04-02T12:51'),
  approvedDate: dayjs('2023-04-02T19:06'),
  freeField2: 'Functionality composite',
  freeField3: 'invoice',
  isDeleted: dayjs('2023-04-02'),
};

export const sampleWithNewData: NewSchool = {
  recordUniqueIdentifier: 'b40d5d48-9da4-4fe5-9b8d-a43ec05f7e8f',
  schoolId: 58180,
  schoolCode: 'synergies haptic neural',
  schoolName: 'Trafficway visionary',
  status: RecordStatus['INACTIVE'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
