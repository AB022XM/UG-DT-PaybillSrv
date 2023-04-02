import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IValidateCustomerById } from '../validate-customer-by-id.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../validate-customer-by-id.test-samples';

import { ValidateCustomerByIdService } from './validate-customer-by-id.service';

const requireRestSample: IValidateCustomerById = {
  ...sampleWithRequiredData,
};

describe('ValidateCustomerById Service', () => {
  let service: ValidateCustomerByIdService;
  let httpMock: HttpTestingController;
  let expectedResult: IValidateCustomerById | IValidateCustomerById[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ValidateCustomerByIdService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ValidateCustomerById', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const validateCustomerById = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(validateCustomerById).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ValidateCustomerById', () => {
      const validateCustomerById = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(validateCustomerById).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ValidateCustomerById', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ValidateCustomerById', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ValidateCustomerById', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addValidateCustomerByIdToCollectionIfMissing', () => {
      it('should add a ValidateCustomerById to an empty array', () => {
        const validateCustomerById: IValidateCustomerById = sampleWithRequiredData;
        expectedResult = service.addValidateCustomerByIdToCollectionIfMissing([], validateCustomerById);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(validateCustomerById);
      });

      it('should not add a ValidateCustomerById to an array that contains it', () => {
        const validateCustomerById: IValidateCustomerById = sampleWithRequiredData;
        const validateCustomerByIdCollection: IValidateCustomerById[] = [
          {
            ...validateCustomerById,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addValidateCustomerByIdToCollectionIfMissing(validateCustomerByIdCollection, validateCustomerById);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ValidateCustomerById to an array that doesn't contain it", () => {
        const validateCustomerById: IValidateCustomerById = sampleWithRequiredData;
        const validateCustomerByIdCollection: IValidateCustomerById[] = [sampleWithPartialData];
        expectedResult = service.addValidateCustomerByIdToCollectionIfMissing(validateCustomerByIdCollection, validateCustomerById);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(validateCustomerById);
      });

      it('should add only unique ValidateCustomerById to an array', () => {
        const validateCustomerByIdArray: IValidateCustomerById[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const validateCustomerByIdCollection: IValidateCustomerById[] = [sampleWithRequiredData];
        expectedResult = service.addValidateCustomerByIdToCollectionIfMissing(validateCustomerByIdCollection, ...validateCustomerByIdArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const validateCustomerById: IValidateCustomerById = sampleWithRequiredData;
        const validateCustomerById2: IValidateCustomerById = sampleWithPartialData;
        expectedResult = service.addValidateCustomerByIdToCollectionIfMissing([], validateCustomerById, validateCustomerById2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(validateCustomerById);
        expect(expectedResult).toContain(validateCustomerById2);
      });

      it('should accept null and undefined values', () => {
        const validateCustomerById: IValidateCustomerById = sampleWithRequiredData;
        expectedResult = service.addValidateCustomerByIdToCollectionIfMissing([], null, validateCustomerById, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(validateCustomerById);
      });

      it('should return initial array if no ValidateCustomerById is added', () => {
        const validateCustomerByIdCollection: IValidateCustomerById[] = [sampleWithRequiredData];
        expectedResult = service.addValidateCustomerByIdToCollectionIfMissing(validateCustomerByIdCollection, undefined, null);
        expect(expectedResult).toEqual(validateCustomerByIdCollection);
      });
    });

    describe('compareValidateCustomerById', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareValidateCustomerById(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareValidateCustomerById(entity1, entity2);
        const compareResult2 = service.compareValidateCustomerById(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareValidateCustomerById(entity1, entity2);
        const compareResult2 = service.compareValidateCustomerById(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareValidateCustomerById(entity1, entity2);
        const compareResult2 = service.compareValidateCustomerById(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
