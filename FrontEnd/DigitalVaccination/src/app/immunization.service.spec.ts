import { TestBed } from '@angular/core/testing';

import { ImmunizationService } from './immunization.service';

describe('ImmunizationService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ImmunizationService = TestBed.get(ImmunizationService);
    expect(service).toBeTruthy();
  });
});
