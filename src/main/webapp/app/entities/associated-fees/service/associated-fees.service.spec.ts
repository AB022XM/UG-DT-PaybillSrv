import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IAssociatedFees } from '../associated-fees.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../associated-fees.test-samples';

import { AssociatedFeesService, RestAssociatedFees } from './associated-fees.service';

const requireRestSample: RestAssociatedFees = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.format(DATE_FORMAT),
  updatedAt: sampleWithRequiredData.updatedAt?.format(DATE_FORMAT),
  isDeleted: sampleWithRequiredData.isDeleted?.format(DATE_FORMAT),
};

describe('AssociatedFees Service', () => {
  let service: AssociatedFeesService;
  let httpMock: HttpTestingController;
  let expectedResult: IAssociatedFees | IAssociatedFees[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AssociatedFeesService);
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

    it('should create a AssociatedFees', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const associatedFees = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(associatedFees).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AssociatedFees', () => {
      const associatedFees = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(associatedFees).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AssociatedFees', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AssociatedFees', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AssociatedFees', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAssociatedFeesToCollectionIfMissing', () => {
      it('should add a AssociatedFees to an empty array', () => {
        const associatedFees: IAssociatedFees = sampleWithRequiredData;
        expectedResult = service.addAssociatedFeesToCollectionIfMissing([], associatedFees);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(associatedFees);
      });

      it('should not add a AssociatedFees to an array that contains it', () => {
        const associatedFees: IAssociatedFees = sampleWithRequiredData;
        const associatedFeesCollection: IAssociatedFees[] = [
          {
            ...associatedFees,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAssociatedFeesToCollectionIfMissing(associatedFeesCollection, associatedFees);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AssociatedFees to an array that doesn't contain it", () => {
        const associatedFees: IAssociatedFees = sampleWithRequiredData;
        const associatedFeesCollection: IAssociatedFees[] = [sampleWithPartialData];
        expectedResult = service.addAssociatedFeesToCollectionIfMissing(associatedFeesCollection, associatedFees);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(associatedFees);
      });

      it('should add only unique AssociatedFees to an array', () => {
        const associatedFeesArray: IAssociatedFees[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const associatedFeesCollection: IAssociatedFees[] = [sampleWithRequiredData];
        expectedResult = service.addAssociatedFeesToCollectionIfMissing(associatedFeesCollection, ...associatedFeesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const associatedFees: IAssociatedFees = sampleWithRequiredData;
        const associatedFees2: IAssociatedFees = sampleWithPartialData;
        expectedResult = service.addAssociatedFeesToCollectionIfMissing([], associatedFees, associatedFees2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(associatedFees);
        expect(expectedResult).toContain(associatedFees2);
      });

      it('should accept null and undefined values', () => {
        const associatedFees: IAssociatedFees = sampleWithRequiredData;
        expectedResult = service.addAssociatedFeesToCollectionIfMissing([], null, associatedFees, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(associatedFees);
      });

      it('should return initial array if no AssociatedFees is added', () => {
        const associatedFeesCollection: IAssociatedFees[] = [sampleWithRequiredData];
        expectedResult = service.addAssociatedFeesToCollectionIfMissing(associatedFeesCollection, undefined, null);
        expect(expectedResult).toEqual(associatedFeesCollection);
      });
    });

    describe('compareAssociatedFees', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAssociatedFees(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAssociatedFees(entity1, entity2);
        const compareResult2 = service.compareAssociatedFees(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAssociatedFees(entity1, entity2);
        const compareResult2 = service.compareAssociatedFees(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAssociatedFees(entity1, entity2);
        const compareResult2 = service.compareAssociatedFees(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
