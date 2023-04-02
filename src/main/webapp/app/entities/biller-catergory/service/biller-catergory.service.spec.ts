import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBillerCatergory } from '../biller-catergory.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../biller-catergory.test-samples';

import { BillerCatergoryService } from './biller-catergory.service';

const requireRestSample: IBillerCatergory = {
  ...sampleWithRequiredData,
};

describe('BillerCatergory Service', () => {
  let service: BillerCatergoryService;
  let httpMock: HttpTestingController;
  let expectedResult: IBillerCatergory | IBillerCatergory[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BillerCatergoryService);
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

    it('should create a BillerCatergory', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const billerCatergory = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(billerCatergory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BillerCatergory', () => {
      const billerCatergory = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(billerCatergory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BillerCatergory', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BillerCatergory', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a BillerCatergory', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addBillerCatergoryToCollectionIfMissing', () => {
      it('should add a BillerCatergory to an empty array', () => {
        const billerCatergory: IBillerCatergory = sampleWithRequiredData;
        expectedResult = service.addBillerCatergoryToCollectionIfMissing([], billerCatergory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(billerCatergory);
      });

      it('should not add a BillerCatergory to an array that contains it', () => {
        const billerCatergory: IBillerCatergory = sampleWithRequiredData;
        const billerCatergoryCollection: IBillerCatergory[] = [
          {
            ...billerCatergory,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addBillerCatergoryToCollectionIfMissing(billerCatergoryCollection, billerCatergory);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BillerCatergory to an array that doesn't contain it", () => {
        const billerCatergory: IBillerCatergory = sampleWithRequiredData;
        const billerCatergoryCollection: IBillerCatergory[] = [sampleWithPartialData];
        expectedResult = service.addBillerCatergoryToCollectionIfMissing(billerCatergoryCollection, billerCatergory);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(billerCatergory);
      });

      it('should add only unique BillerCatergory to an array', () => {
        const billerCatergoryArray: IBillerCatergory[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const billerCatergoryCollection: IBillerCatergory[] = [sampleWithRequiredData];
        expectedResult = service.addBillerCatergoryToCollectionIfMissing(billerCatergoryCollection, ...billerCatergoryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const billerCatergory: IBillerCatergory = sampleWithRequiredData;
        const billerCatergory2: IBillerCatergory = sampleWithPartialData;
        expectedResult = service.addBillerCatergoryToCollectionIfMissing([], billerCatergory, billerCatergory2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(billerCatergory);
        expect(expectedResult).toContain(billerCatergory2);
      });

      it('should accept null and undefined values', () => {
        const billerCatergory: IBillerCatergory = sampleWithRequiredData;
        expectedResult = service.addBillerCatergoryToCollectionIfMissing([], null, billerCatergory, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(billerCatergory);
      });

      it('should return initial array if no BillerCatergory is added', () => {
        const billerCatergoryCollection: IBillerCatergory[] = [sampleWithRequiredData];
        expectedResult = service.addBillerCatergoryToCollectionIfMissing(billerCatergoryCollection, undefined, null);
        expect(expectedResult).toEqual(billerCatergoryCollection);
      });
    });

    describe('compareBillerCatergory', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareBillerCatergory(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareBillerCatergory(entity1, entity2);
        const compareResult2 = service.compareBillerCatergory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareBillerCatergory(entity1, entity2);
        const compareResult2 = service.compareBillerCatergory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareBillerCatergory(entity1, entity2);
        const compareResult2 = service.compareBillerCatergory(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
