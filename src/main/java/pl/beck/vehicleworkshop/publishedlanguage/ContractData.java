package pl.beck.vehicleworkshop.publishedlanguage;

import lombok.Getter;
import lombok.Value;
import pl.beck.vehicleworkshop.sharedkernel.Money;

import javax.persistence.Embeddable;
import java.util.List;

@Embeddable
@Getter
@Value
public class ContractData {

    private String clientPersonalNumber;

    private String vin;

    private ContractNumber contractNumber;

    private final List<Repair> repairs;

    public ContractData(final String clientPersonalNumber, final String vin, final ContractNumber contractNumber,
                        final List<Repair> repairs) {
        this.clientPersonalNumber = clientPersonalNumber;
        this.vin = vin;
        this.contractNumber = contractNumber;
        this.repairs = repairs;
    }

    public static Repair createPaidRepair(ContractNumber contractNumber, Money negotiatedPrice, RepairServiceCatalogNumber catalogNumber) {
        return Repair.paid(contractNumber, negotiatedPrice, catalogNumber);
    }

    public static Repair createGuaranteedPaidRepair(ContractNumber contractNumber, RepairServiceCatalogNumber catalogNumber, boolean used) {
        return Repair.guaranteed(contractNumber, catalogNumber, used);
    }

    @Value
    static public class Repair {

        private final ContractNumber contractNumber;

        private final RepairServiceCatalogNumber catalogNumber;

        private boolean guaranteed;

        private Boolean used;

        private final Money negotiatedPrice;

        static Repair paid(ContractNumber contractNumber, Money negotiatedPrice, RepairServiceCatalogNumber catalogNumber) {
            return new Repair(contractNumber, negotiatedPrice, catalogNumber);
        }

        static Repair guaranteed(ContractNumber contractNumber, RepairServiceCatalogNumber catalogNumber, boolean used) {
            return new Repair(contractNumber, catalogNumber, used);
        }

        private Repair(ContractNumber contractNumber, Money negotiatedPrice, RepairServiceCatalogNumber catalogNumber) {
            this.contractNumber = contractNumber;
            this.catalogNumber = catalogNumber;
            this.guaranteed = false;
            this.negotiatedPrice = negotiatedPrice;
            this.used = null;
        }

        private Repair(ContractNumber contractNumber, RepairServiceCatalogNumber catalogNumber, boolean used) {
            this.contractNumber = contractNumber;
            this.catalogNumber = catalogNumber;
            this.guaranteed = true;
            this.negotiatedPrice = null;
            this.used = used;
        }

        public Money getNegotiatedPrice() {
            if(guaranteed)
                throw new UnsupportedOperationException("Can not call this method for guaranteed repair");
            return negotiatedPrice;
        }

        private boolean isUsed() {
            return used;
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
//TODO zbyt wiele zachowania w tym obiekcie... zastanowic sie czy ma on jaki kolwiek sens...