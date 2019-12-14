import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RiskInfoVacPageComponent } from './risk-info-vac-page.component';

describe('RiskInfoVacPageComponent', () => {
  let component: RiskInfoVacPageComponent;
  let fixture: ComponentFixture<RiskInfoVacPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RiskInfoVacPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RiskInfoVacPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
