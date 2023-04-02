import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IBiller } from '../biller.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../biller.test-samples';

import { BillerService, RestBiller } from './biller.service';

const requireRestSample: RestBiller = {
  ...sampleWithRequiredData,
  dateOfBirth: sampleWithRequiredData.dateOfBirth?.format(DATE_FORMAT),
  isDeleted: sampleWithRequiredData.isDeleted?.format(DATE_FORMAT),
};

describe('Biller Service', () => {
  let service: BillerService;
  let httpMock: HttpTestingController;
  let expectedResult: IBiller | IBiller[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BillerService);
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

    it('should create a Biller', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const biller = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(biller).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Biller', () => {
      const biller = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(biller).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Biller', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Biller', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Biller', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addBillerToCollectionIfMissing', () => {
      it('should add a Biller to an empty array', () => {
        const biller: IBiller = sampleWithRequiredData;
        expectedResult = service.addBillerToCollectionIfMissing([], biller);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(biller);
      });

      it('should not add a Biller to an array that contains it', () => {
        const biller: IBiller = sampleWithRequiredData;
        const billerCollection: IBiller[] = [
          {
            ...biller,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addBillerToCollectionIfMissing(billerCollection, biller);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Biller to an array that doesn't contain it", () => {
        const biller: IBiller = sampleWithRequiredData;
        const billerCollection: IBiller[] = [sampleWithPartialData];
        expectedResult = service.addBillerToCollectionIfMissing(billerCollection, biller);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(biller);
      });

      it('should add only unique Biller to an array', () => {
        const billerArray: IBiller[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const billerCollection: IBiller[] = [sampleWithRequiredData];
        expectedResult = service.addBillerToCollectionIfMissing(billerCollection, ...billerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const biller: IBiller = sampleWithRequiredData;
        const biller2: IBiller = sampleWithPartialData;
        expectedResult = service.addBillerToCollectionIfMissing([], biller, biller2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(biller);
        expect(expectedResult).toContain(biller2);
      });

      it('should accept null and undefined values', () => {
        const biller: IBiller = sampleWithRequiredData;
        expectedResult = service.addBillerToCollectionIfMissing([], null, biller, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(biller);
      });

      it('should return initial array if no Biller is added', () => {
        const billerCollection: IBiller[] = [sampleWithRequiredData];
        expectedResult = service.addBillerToCollectionIfMissing(billerCollection, undefined, null);
        expect(expectedResult).toEqual(billerCollection);
      });
    });

    describe('compareBiller', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareBiller(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareBiller(entity1, entity2);
        const compareResult2 = service.compareBiller(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareBiller(entity1, entity2);
        const compareResult2 = service.compareBiller(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareBiller(entity1, entity2);
        const compareResult2 = service.compareBiller(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
