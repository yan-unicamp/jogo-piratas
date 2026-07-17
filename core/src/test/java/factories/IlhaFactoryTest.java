package factories;

import progressao.Ilha;
import progressao.IlhaEnum;
import progressao.IlhasGenericasEnum;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IlhaFactoryTest {

    @Test
    void deveCriarTodasIlhasEspecificas() {
        for (IlhaEnum e : IlhaEnum.values()) {
            Ilha ilha = IlhaFactory.criar(e);
            assertNotNull(ilha, "Deve criar a ilha: " + e.name());
            assertNotNull(ilha.getNome());
            assertFalse(ilha.getRodadas().isEmpty());
        }
    }

    @Test
    void deveCriarTodasIlhasGenericas() {
        for (IlhasGenericasEnum e : IlhasGenericasEnum.values()) {
            Ilha ilha = IlhaFactory.criarGenerica(e);
            assertNotNull(ilha, "Deve criar a ilha generica: " + e.name());
            assertNotNull(ilha.getNome());
            assertFalse(ilha.getRodadas().isEmpty());
        }
    }
}
