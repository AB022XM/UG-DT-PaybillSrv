jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { BillerCatergoryService } from '../service/biller-catergory.service';

import { BillerCatergoryDeleteDialogComponent } from './biller-catergory-delete-dialog.component';

describe('BillerCatergory Management Delete Component', () => {
  let comp: BillerCatergoryDeleteDialogComponent;
  let fixture: ComponentFixture<BillerCatergoryDeleteDialogComponent>;
  let service: BillerCatergoryService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [BillerCatergoryDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(BillerCatergoryDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BillerCatergoryDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(BillerCatergoryService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
