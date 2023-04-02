import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../school.test-samples';

import { SchoolFormService } from './school-form.service';

describe('School Form Service', () => {
  let service: SchoolFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SchoolFormService);
  });

  describe('Service methods', () => {
    describe('createSchoolFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSchoolFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            recordUniqueIdentifier: expect.any(Object),
            schoolId: expect.any(Object),
            schoolCode: expect.any(Object),
            schoolPhoneNumber: expect.any(Object),
            schoolAlternativePhoneNumber: expect.any(Object),
            schoolemailAddess: expect.any(Object),
            schoolName: expect.any(Object),
            status: expect.any(Object),
            freeField1: expect.any(Object),
            registrationDate: expect.any(Object),
            approvedDate: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            isDeleted: expect.any(Object),
          })
        );
      });

      it('passing ISchool should create a new form with FormGroup', () => {
        const formGroup = service.createSchoolFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            recordUniqueIdentifier: expect.any(Object),
            schoolId: expect.any(Object),
            schoolCode: expect.any(Object),
            schoolPhoneNumber: expect.any(Object),
            schoolAlternativePhoneNumber: expect.any(Object),
            schoolemailAddess: expect.any(Object),
            schoolName: expect.any(Object),
            status: expect.any(Object),
            freeField1: expect.any(Object),
            registrationDate: expect.any(Object),
            approvedDate: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            isDeleted: expect.any(Object),
          })
        );
      });
    });

    describe('getSchool', () => {
      it('should return NewSchool for default School initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSchoolFormGroup(sampleWithNewData);

        const school = service.getSchool(formGroup) as any;

        expect(school).toMatchObject(sampleWithNewData);
      });

      it('should return NewSchool for empty School initial value', () => {
        const formGroup = service.createSchoolFormGroup();

        const school = service.getSchool(formGroup) as any;

        expect(school).toMatchObject({});
      });

      it('should return ISchool', () => {
        const formGroup = service.createSchoolFormGroup(sampleWithRequiredData);

        const school = service.getSchool(formGroup) as any;

        expect(school).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISchool should not enable id FormControl', () => {
        const formGroup = service.createSchoolFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSchool should disable id FormControl', () => {
        const formGroup = service.createSchoolFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
