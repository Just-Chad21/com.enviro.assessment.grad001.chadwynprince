package myapp.services;

import myapp.model.RecyclingTip;
import myapp.repository.RecyclingTipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecyclingTipService {

    @Autowired
    private RecyclingTipRepository repository;

    public RecyclingTip createRecyclingTip(RecyclingTip recyclingTip) {
        if (recyclingTip.getTip() == null || recyclingTip.getTip().trim().isEmpty()) {
            return null;
        }
        return repository.save(recyclingTip);
    }

    public List<RecyclingTip> getAllRecyclingTips() {
        return repository.findAll();
    }

    public Optional<RecyclingTip> getRecyclingTipById(Long id) {
        return repository.findById(id);
    }

    public RecyclingTip updateRecyclingTip(Long id, RecyclingTip recyclingTipDetails) {
        Optional<RecyclingTip> recyclingTipOptional = repository.findById(id);
        if (recyclingTipOptional.isPresent() && !recyclingTipDetails.getTip().isEmpty()) {
            RecyclingTip recyclingTip = recyclingTipOptional.get();
            recyclingTip.setTip(recyclingTipDetails.getTip());
            return repository.save(recyclingTip);
        } else {
            return null;
        }
    }

    public void deleteRecyclingTip(Long id) {
        repository.deleteById(id);
    }
}
