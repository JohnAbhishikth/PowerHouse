import { DependentSpendLimitDTO } from './DependentSpendLimitDTO';
import { MerchantHotlistDTO } from './merchantHotlistDTO';

export class DependentAccountDTO {
    dependentSpendLimitDTO!: DependentSpendLimitDTO
    merchantHotlistDTO!: MerchantHotlistDTO[]
}