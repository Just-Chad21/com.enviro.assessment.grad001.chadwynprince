package repositoryTests;

import myapp.MyApplication;
import myapp.model.DisposalGuidelines;
import myapp.repository.DisposalGuidelinesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyApplication.class)
@ActiveProfiles("test")
public class DisposalGuidelinesRepositoryTest {
    @Autowired
    private DisposalGuidelinesRepository disposalGuidelinesRepository;

    @Test
    public void testSaveAndFindById() {
        DisposalGuidelines guidelines = new DisposalGuidelines();
        guidelines.setDisposalGuideline("Dispose in a green bin");

        DisposalGuidelines savedGuidelines = disposalGuidelinesRepository.save(guidelines);
        Optional<DisposalGuidelines> foundGuidelines = disposalGuidelinesRepository.findById(savedGuidelines.getId());

        assertTrue(foundGuidelines.isPresent());
        assertEquals(savedGuidelines.getId(), foundGuidelines.get().getId());
        assertEquals("Dispose in a green bin", foundGuidelines.get().getDisposalGuideline());
    }

    @Test
    public void testDelete() {
        DisposalGuidelines guidelines = new DisposalGuidelines();
        guidelines.setDisposalGuideline("Dispose in a black bin");

        DisposalGuidelines savedGuidelines = disposalGuidelinesRepository.save(guidelines);
        disposalGuidelinesRepository.delete(savedGuidelines);

        Optional<DisposalGuidelines> foundGuidelines = disposalGuidelinesRepository.findById(savedGuidelines.getId());
        assertFalse(foundGuidelines.isPresent());
    }

    @Test
    public void testFindAll() {
        DisposalGuidelines guidelines1 = new DisposalGuidelines();
        guidelines1.setDisposalGuideline("Dispose in a blue bin");
        disposalGuidelinesRepository.save(guidelines1);

        DisposalGuidelines guidelines2 = new DisposalGuidelines();
        guidelines2.setDisposalGuideline("Dispose in a red bin");
        disposalGuidelinesRepository.save(guidelines2);

        Iterable<DisposalGuidelines> guidelines = disposalGuidelinesRepository.findAll();
        int count = 0;
        for (DisposalGuidelines guideline : guidelines) {
            count++;
        }
        assertEquals(2, count);
    }
}
