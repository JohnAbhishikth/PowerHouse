import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateDependentComponent } from './create-dependent.component';

describe('CreateDependentComponent', () => {
  let component: CreateDependentComponent;
  let fixture: ComponentFixture<CreateDependentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateDependentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateDependentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
