package myapp.services;

import myapp.model.DisposalGuidelines;
import myapp.repository.DisposalGuidelinesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisposalGuidelinesService {
    @Autowired
    private DisposalGuidelinesRepository disposalGuidelinesRepository;

    public List<DisposalGuidelines> getAllDisposalGuidelines() {
        return disposalGuidelinesRepository.findAll();
    }

    public Optional<DisposalGuidelines> getDisposalGuidelinesById(Long id) {
        return disposalGuidelinesRepository.findById(id);
    }

    public DisposalGuidelines createDisposalGuidelines(DisposalGuidelines disposalGuidelines) {
        return disposalGuidelinesRepository.save(disposalGuidelines);
    }

    public DisposalGuidelines updateDisposalGuidelines(Long id, DisposalGuidelines disposalGuidelines) {
        Optional<DisposalGuidelines> optionalDisposalGuidelines = disposalGuidelinesRepository.findById(id);
        if (optionalDisposalGuidelines.isPresent()) {
            DisposalGuidelines existingDisposalGuidelines = optionalDisposalGuidelines.get();
            existingDisposalGuidelines.setDisposalGuideline(disposalGuidelines.getDisposalGuideline());
            return disposalGuidelinesRepository.save(existingDisposalGuidelines);
        } else {
            return null;
        }
    }

    public void deleteDisposalGuidelines(Long id) {
        disposalGuidelinesRepository.deleteById(id);
    }
}
