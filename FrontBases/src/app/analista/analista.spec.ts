import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Analista } from './analista';

describe('Analista', () => {
  let component: Analista;
  let fixture: ComponentFixture<Analista>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Analista],
    }).compileComponents();

    fixture = TestBed.createComponent(Analista);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
