package repositoryTests;

import myapp.MyApplication;
import myapp.model.WasteCategory;
import myapp.repository.WasteCategoryRepository;
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
public class WasteCategoryRepositoryTest {
    @Autowired
    private WasteCategoryRepository wasteCategoryRepository;

    @Test
    void testSaveAndFindById() {
        WasteCategory wasteCategory = new WasteCategory();
        wasteCategory.setName("Recyclable");

        WasteCategory savedCategory = wasteCategoryRepository.save(wasteCategory);
        Optional<WasteCategory> foundCategory = wasteCategoryRepository.findById(savedCategory.getId());

        assertTrue(foundCategory.isPresent());
        assertEquals(savedCategory.getId(), foundCategory.get().getId());
        assertEquals("Recyclable", foundCategory.get().getName());
    }

    @Test
    void testDelete() {
        WasteCategory wasteCategory = new WasteCategory();
        wasteCategory.setName("Non-Recyclable");

        WasteCategory savedCategory = wasteCategoryRepository.save(wasteCategory);
        wasteCategoryRepository.delete(savedCategory);

        Optional<WasteCategory> foundCategory = wasteCategoryRepository.findById(savedCategory.getId());
        assertFalse(foundCategory.isPresent());
    }

    @Test
    void testFindAll() {
        WasteCategory category1 = new WasteCategory();
        category1.setName("Recyclable");
        wasteCategoryRepository.save(category1);

        WasteCategory category2 = new WasteCategory();
        category2.setName("Non-Recyclable");
        wasteCategoryRepository.save(category2);

        Iterable<WasteCategory> categories = wasteCategoryRepository.findAll();
        int count = 0;
        for (WasteCategory category : categories) {
            count++;
        }
        assertEquals(2, count);
    }
}
