import { TestBed, inject } from '@angular/core/testing';

import { SharedNodeNavbarService } from './shared-node-navbar.service';

describe('SharedNodeNavbarService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SharedNodeNavbarService]
    });
  });

  it('should ...', inject([SharedNodeNavbarService], (service: SharedNodeNavbarService) => {
    expect(service).toBeTruthy();
  }));
});
