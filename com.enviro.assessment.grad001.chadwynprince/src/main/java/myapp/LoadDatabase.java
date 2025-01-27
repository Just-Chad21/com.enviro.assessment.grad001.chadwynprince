package myapp;

import myapp.model.DisposalGuidelines;
import myapp.model.RecyclingTip;
import myapp.model.WasteCategory;
import myapp.repository.DisposalGuidelinesRepository;
import myapp.repository.RecyclingTipRepository;
import myapp.repository.WasteCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test") // Exclude this configuration when the "test" profile is active
public class LoadDatabase {

    private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    public CommandLineRunner initDatabase(DisposalGuidelinesRepository disposalGuidelinesRepository, RecyclingTipRepository recyclingTipRepository, WasteCategoryRepository wasteCategoryRepository){
        return args -> {
            // Preloading the disposal Guidelines repo
            disposalGuidelinesRepository.save(new DisposalGuidelines(1,
                    "Don't throw plastics and metals into same trash can."));
            disposalGuidelinesRepository.save(new DisposalGuidelines(2,
                    "Don't throw combustible material in plastic trash can."));
            disposalGuidelinesRepository.findAll().forEach(disposalGuideline -> logger.info("Preloaded " + disposalGuideline));

            // Preloading the recycling tips repo
            recyclingTipRepository.save(new RecyclingTip(1,
                    "Get separate trash cans for different materials"));
            recyclingTipRepository.save(new RecyclingTip(2,
                    "Reuse glass jars as storage containers for pantry items or DIY projects instead of throwing them away."));
            recyclingTipRepository.findAll().forEach(recyclingTip -> logger.info("Preloaded " + recyclingTip));

            // Preloading the waste category repo
            wasteCategoryRepository.save(new WasteCategory(1,
                    "Biodegradable Waste"));
            wasteCategoryRepository.save(new WasteCategory(2,
                    "Non-Biodegradable Waste"));
            wasteCategoryRepository.findAll().forEach(wasteCategory -> logger.info("Preloaded " + wasteCategory));

        };
    }
}
