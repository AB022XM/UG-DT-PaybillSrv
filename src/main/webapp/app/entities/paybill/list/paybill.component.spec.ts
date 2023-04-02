import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PaybillService } from '../service/paybill.service';

import { PaybillComponent } from './paybill.component';

describe('Paybill Management Component', () => {
  let comp: PaybillComponent;
  let fixture: ComponentFixture<PaybillComponent>;
  let service: PaybillService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'paybill', component: PaybillComponent }]), HttpClientTestingModule],
      declarations: [PaybillComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(PaybillComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaybillComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PaybillService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.paybills?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to paybillService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPaybillIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPaybillIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
