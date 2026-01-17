package alt.portfolio.builder.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alt.portfolio.builder.entity.Profile;
import alt.portfolio.builder.entity.User;
import alt.portfolio.builder.repository.ProfileRepository;

@Service
public class ProfileServices {

	@Autowired
	private ProfileRepository profileRepository;

	public List<Profile> findByOwner(User owner) {
		return profileRepository.findByOwner(owner);
	}

	public Profile createProfile(User owner, String name, String description) {
		Profile profile = new Profile();
		profile.setOwner(owner);
		profile.setName(name);
		profile.setDescription(description);
		return profileRepository.save(profile);
	}

	public Profile findById(UUID id) {
		return profileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
	}

	/* ================= DELETE ================= */

	public void deleteProfile(UUID profileId, UUID ownerId) {
		Profile profile = findById(profileId);

		if (!profile.getOwner().getId().equals(ownerId)) {
			throw new RuntimeException("Forbidden");
		}

		profileRepository.delete(profile);
	}

	/* ================= EDIT ================= */

	public Profile editProfile(UUID profileId, String name, String description, UUID ownerId) {
		Profile profile = findById(profileId);

		if (!profile.getOwner().getId().equals(ownerId)) {
			throw new RuntimeException("Forbidden");
		}

		profile.setName(name);
		profile.setDescription(description);

		return profileRepository.save(profile);
	}
}
