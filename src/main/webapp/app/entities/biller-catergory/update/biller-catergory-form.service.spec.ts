import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../biller-catergory.test-samples';

import { BillerCatergoryFormService } from './biller-catergory-form.service';

describe('BillerCatergory Form Service', () => {
  let service: BillerCatergoryFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BillerCatergoryFormService);
  });

  describe('Service methods', () => {
    describe('createBillerCatergoryFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBillerCatergoryFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            recordUniqueIdentifier: expect.any(Object),
            categoryId: expect.any(Object),
            categoryCode: expect.any(Object),
            categoryName: expect.any(Object),
            categoryDescription: expect.any(Object),
            status: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            isDeleted: expect.any(Object),
          })
        );
      });

      it('passing IBillerCatergory should create a new form with FormGroup', () => {
        const formGroup = service.createBillerCatergoryFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            recordUniqueIdentifier: expect.any(Object),
            categoryId: expect.any(Object),
            categoryCode: expect.any(Object),
            categoryName: expect.any(Object),
            categoryDescription: expect.any(Object),
            status: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            isDeleted: expect.any(Object),
          })
        );
      });
    });

    describe('getBillerCatergory', () => {
      it('should return NewBillerCatergory for default BillerCatergory initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createBillerCatergoryFormGroup(sampleWithNewData);

        const billerCatergory = service.getBillerCatergory(formGroup) as any;

        expect(billerCatergory).toMatchObject(sampleWithNewData);
      });

      it('should return NewBillerCatergory for empty BillerCatergory initial value', () => {
        const formGroup = service.createBillerCatergoryFormGroup();

        const billerCatergory = service.getBillerCatergory(formGroup) as any;

        expect(billerCatergory).toMatchObject({});
      });

      it('should return IBillerCatergory', () => {
        const formGroup = service.createBillerCatergoryFormGroup(sampleWithRequiredData);

        const billerCatergory = service.getBillerCatergory(formGroup) as any;

        expect(billerCatergory).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBillerCatergory should not enable id FormControl', () => {
        const formGroup = service.createBillerCatergoryFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBillerCatergory should disable id FormControl', () => {
        const formGroup = service.createBillerCatergoryFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
