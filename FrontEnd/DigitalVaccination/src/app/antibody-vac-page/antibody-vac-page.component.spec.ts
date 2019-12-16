import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AntibodyVacPageComponent } from './antibody-vac-page.component';

describe('AntibodyVacPageComponent', () => {
  let component: AntibodyVacPageComponent;
  let fixture: ComponentFixture<AntibodyVacPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AntibodyVacPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AntibodyVacPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
