import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPaymentChannel } from '../payment-channel.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../payment-channel.test-samples';

import { PaymentChannelService } from './payment-channel.service';

const requireRestSample: IPaymentChannel = {
  ...sampleWithRequiredData,
};

describe('PaymentChannel Service', () => {
  let service: PaymentChannelService;
  let httpMock: HttpTestingController;
  let expectedResult: IPaymentChannel | IPaymentChannel[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PaymentChannelService);
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

    it('should create a PaymentChannel', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const paymentChannel = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(paymentChannel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PaymentChannel', () => {
      const paymentChannel = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(paymentChannel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PaymentChannel', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PaymentChannel', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PaymentChannel', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPaymentChannelToCollectionIfMissing', () => {
      it('should add a PaymentChannel to an empty array', () => {
        const paymentChannel: IPaymentChannel = sampleWithRequiredData;
        expectedResult = service.addPaymentChannelToCollectionIfMissing([], paymentChannel);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paymentChannel);
      });

      it('should not add a PaymentChannel to an array that contains it', () => {
        const paymentChannel: IPaymentChannel = sampleWithRequiredData;
        const paymentChannelCollection: IPaymentChannel[] = [
          {
            ...paymentChannel,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPaymentChannelToCollectionIfMissing(paymentChannelCollection, paymentChannel);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PaymentChannel to an array that doesn't contain it", () => {
        const paymentChannel: IPaymentChannel = sampleWithRequiredData;
        const paymentChannelCollection: IPaymentChannel[] = [sampleWithPartialData];
        expectedResult = service.addPaymentChannelToCollectionIfMissing(paymentChannelCollection, paymentChannel);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paymentChannel);
      });

      it('should add only unique PaymentChannel to an array', () => {
        const paymentChannelArray: IPaymentChannel[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const paymentChannelCollection: IPaymentChannel[] = [sampleWithRequiredData];
        expectedResult = service.addPaymentChannelToCollectionIfMissing(paymentChannelCollection, ...paymentChannelArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const paymentChannel: IPaymentChannel = sampleWithRequiredData;
        const paymentChannel2: IPaymentChannel = sampleWithPartialData;
        expectedResult = service.addPaymentChannelToCollectionIfMissing([], paymentChannel, paymentChannel2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paymentChannel);
        expect(expectedResult).toContain(paymentChannel2);
      });

      it('should accept null and undefined values', () => {
        const paymentChannel: IPaymentChannel = sampleWithRequiredData;
        expectedResult = service.addPaymentChannelToCollectionIfMissing([], null, paymentChannel, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paymentChannel);
      });

      it('should return initial array if no PaymentChannel is added', () => {
        const paymentChannelCollection: IPaymentChannel[] = [sampleWithRequiredData];
        expectedResult = service.addPaymentChannelToCollectionIfMissing(paymentChannelCollection, undefined, null);
        expect(expectedResult).toEqual(paymentChannelCollection);
      });
    });

    describe('comparePaymentChannel', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePaymentChannel(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePaymentChannel(entity1, entity2);
        const compareResult2 = service.comparePaymentChannel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePaymentChannel(entity1, entity2);
        const compareResult2 = service.comparePaymentChannel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePaymentChannel(entity1, entity2);
        const compareResult2 = service.comparePaymentChannel(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
