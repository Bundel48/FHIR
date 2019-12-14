import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InfluenzaVacPageComponent } from './influenza-vac-page.component';

describe('InfluenzaVacPageComponent', () => {
  let component: InfluenzaVacPageComponent;
  let fixture: ComponentFixture<InfluenzaVacPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InfluenzaVacPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InfluenzaVacPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
