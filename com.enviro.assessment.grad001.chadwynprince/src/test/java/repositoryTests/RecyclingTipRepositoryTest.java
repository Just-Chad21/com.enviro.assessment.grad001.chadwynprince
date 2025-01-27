package repositoryTests;

import myapp.MyApplication;
import myapp.model.RecyclingTip;
import myapp.repository.RecyclingTipRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyApplication.class)
@ActiveProfiles("test")
public class RecyclingTipRepositoryTest {
    @Autowired
    private RecyclingTipRepository recyclingTipRepository;

    @Test
    public void testSaveAndFindById() {
        RecyclingTip tip = new RecyclingTip();
        tip.setTip("Recycle paper products");

        RecyclingTip savedTip = recyclingTipRepository.save(tip);
        Optional<RecyclingTip> foundTip = recyclingTipRepository.findById(savedTip.getId());

        assertTrue(foundTip.isPresent());
        assertEquals(savedTip.getId(), foundTip.get().getId());
        assertEquals("Recycle paper products", foundTip.get().getTip());
    }

    @Test
    public void testDelete() {
        RecyclingTip tip = new RecyclingTip();
        tip.setTip("Recycle plastic products");

        RecyclingTip savedTip = recyclingTipRepository.save(tip);
        recyclingTipRepository.delete(savedTip);

        Optional<RecyclingTip> foundTip = recyclingTipRepository.findById(savedTip.getId());
        assertFalse(foundTip.isPresent());
    }

    @Test
    public void testFindAll() {
        RecyclingTip tip1 = new RecyclingTip();
        tip1.setTip("Recycle glass products");
        recyclingTipRepository.save(tip1);

        RecyclingTip tip2 = new RecyclingTip();
        tip2.setTip("Recycle metal products");
        recyclingTipRepository.save(tip2);

        Iterable<RecyclingTip> tips = recyclingTipRepository.findAll();
        int count = 0;
        for (RecyclingTip tip : tips) {
            count++;
        }
        assertEquals(2, count);
    }
}
