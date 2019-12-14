import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HumanVacPageComponent } from './human-vac-page.component';

describe('HumanVacPageComponent', () => {
  let component: HumanVacPageComponent;
  let fixture: ComponentFixture<HumanVacPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HumanVacPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HumanVacPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
