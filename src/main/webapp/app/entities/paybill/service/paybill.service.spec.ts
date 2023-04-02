import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IPaybill } from '../paybill.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../paybill.test-samples';

import { PaybillService, RestPaybill } from './paybill.service';

const requireRestSample: RestPaybill = {
  ...sampleWithRequiredData,
  processTimestamp: sampleWithRequiredData.processTimestamp?.format(DATE_FORMAT),
  feeDueFromDate: sampleWithRequiredData.feeDueFromDate?.format(DATE_FORMAT),
  feeDueToDate: sampleWithRequiredData.feeDueToDate?.format(DATE_FORMAT),
};

describe('Paybill Service', () => {
  let service: PaybillService;
  let httpMock: HttpTestingController;
  let expectedResult: IPaybill | IPaybill[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PaybillService);
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

    it('should create a Paybill', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const paybill = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(paybill).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Paybill', () => {
      const paybill = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(paybill).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Paybill', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Paybill', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Paybill', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPaybillToCollectionIfMissing', () => {
      it('should add a Paybill to an empty array', () => {
        const paybill: IPaybill = sampleWithRequiredData;
        expectedResult = service.addPaybillToCollectionIfMissing([], paybill);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paybill);
      });

      it('should not add a Paybill to an array that contains it', () => {
        const paybill: IPaybill = sampleWithRequiredData;
        const paybillCollection: IPaybill[] = [
          {
            ...paybill,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPaybillToCollectionIfMissing(paybillCollection, paybill);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Paybill to an array that doesn't contain it", () => {
        const paybill: IPaybill = sampleWithRequiredData;
        const paybillCollection: IPaybill[] = [sampleWithPartialData];
        expectedResult = service.addPaybillToCollectionIfMissing(paybillCollection, paybill);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paybill);
      });

      it('should add only unique Paybill to an array', () => {
        const paybillArray: IPaybill[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const paybillCollection: IPaybill[] = [sampleWithRequiredData];
        expectedResult = service.addPaybillToCollectionIfMissing(paybillCollection, ...paybillArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const paybill: IPaybill = sampleWithRequiredData;
        const paybill2: IPaybill = sampleWithPartialData;
        expectedResult = service.addPaybillToCollectionIfMissing([], paybill, paybill2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paybill);
        expect(expectedResult).toContain(paybill2);
      });

      it('should accept null and undefined values', () => {
        const paybill: IPaybill = sampleWithRequiredData;
        expectedResult = service.addPaybillToCollectionIfMissing([], null, paybill, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paybill);
      });

      it('should return initial array if no Paybill is added', () => {
        const paybillCollection: IPaybill[] = [sampleWithRequiredData];
        expectedResult = service.addPaybillToCollectionIfMissing(paybillCollection, undefined, null);
        expect(expectedResult).toEqual(paybillCollection);
      });
    });

    describe('comparePaybill', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePaybill(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePaybill(entity1, entity2);
        const compareResult2 = service.comparePaybill(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePaybill(entity1, entity2);
        const compareResult2 = service.comparePaybill(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePaybill(entity1, entity2);
        const compareResult2 = service.comparePaybill(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
