import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { BillerCatergoryService } from '../service/biller-catergory.service';

import { BillerCatergoryComponent } from './biller-catergory.component';

describe('BillerCatergory Management Component', () => {
  let comp: BillerCatergoryComponent;
  let fixture: ComponentFixture<BillerCatergoryComponent>;
  let service: BillerCatergoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'biller-catergory', component: BillerCatergoryComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [BillerCatergoryComponent],
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
      .overrideTemplate(BillerCatergoryComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BillerCatergoryComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(BillerCatergoryService);

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
    expect(comp.billerCatergories?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to billerCatergoryService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getBillerCatergoryIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getBillerCatergoryIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
