package pl.beck.vehicleworkshop.publishedlanguage;

import lombok.Value;
import pl.beck.vehicleworkshop.sharedkernel.Money;

import java.util.List;

@Value
public class ContractData {

    private String clientPersonalNumber;

    private String vin;

    private ContractNumber contractNumber;

    private final List<Repair> repairs;

    public static Repair createGuaranteedRepair(ContractNumber contractNumber, RepairServiceCatalogNumber catalogNumber) {
        return Repair.paid(contractNumber, catalogNumber);
    }

    public static Repair createPaidRepair(ContractNumber contractNumber, Money negotiatedPrice, RepairServiceCatalogNumber catalogNumbe) {
        return Repair.guaranteed(contractNumber, negotiatedPrice, catalogNumbe);
    }

    @Value
    static public class Repair {

        private final ContractNumber contractNumber;

        private final RepairServiceCatalogNumber catalogNumber;

        private boolean guaranteed;

        private final Money negotiatedPrice;

        static Repair paid(ContractNumber contractNumber, RepairServiceCatalogNumber catalogNumber) {
            return new Repair(contractNumber,catalogNumber);
        }

        static Repair guaranteed(ContractNumber contractNumber, Money negotiatedPrice, RepairServiceCatalogNumber catalogNumber) {
            return new Repair(contractNumber, negotiatedPrice, catalogNumber);
        }

        private Repair(ContractNumber contractNumber, Money negotiatedPrice, RepairServiceCatalogNumber catalogNumber) {
            this.contractNumber = contractNumber;
            this.catalogNumber = catalogNumber;
            this.guaranteed = false;
            this.negotiatedPrice = negotiatedPrice;
        }

        private Repair(ContractNumber contractNumber, RepairServiceCatalogNumber catalogNumber) {
            this.contractNumber = contractNumber;
            this.catalogNumber = catalogNumber;
            this.guaranteed = true;
            negotiatedPrice = null;
        }

        public Money getNegotiatedPrice() {
            if(guaranteed)
                throw new UnsupportedOperationException("Can not call this method for guaranteed repair");
            return negotiatedPrice;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        final ContractData that = (ContractData) o;

        if (!clientPersonalNumber.equals(that.clientPersonalNumber)) return false;
        if (!vin.equals(that.vin)) return false;
        if (!contractNumber.equals(that.contractNumber)) return false;
        return repairs.equals(that.repairs);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + clientPersonalNumber.hashCode();
        result = 31 * result + vin.hashCode();
        result = 31 * result + contractNumber.hashCode();
        result = 31 * result + repairs.hashCode();
        return result;
    }
}
