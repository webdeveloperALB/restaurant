package al.student.service;

import al.student.dto.AuthResponse;
import al.student.dto.LoginRequest;
import al.student.dto.RegisterRequest;
import al.student.model.Perdoruesi;
import al.student.model.Role;
import al.student.repository.PerdoruesiRepository;
import al.student.repository.RoleRepository;
import al.student.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService {
    private final PerdoruesiRepository perdoruesiRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(PerdoruesiRepository perdoruesiRepository,
                      RoleRepository roleRepository,
                      PasswordEncoder passwordEncoder,
                      JwtUtil jwtUtil) {
        this.perdoruesiRepository = perdoruesiRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (perdoruesiRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse("Email-i ekziston tashmë në sistem");
        }

        Optional<Role> roleOpt = roleRepository.findByEmri(request.getRoli());
        if (roleOpt.isEmpty()) {
            return new AuthResponse("Roli i specifikuar nuk ekziston");
        }

        Perdoruesi perdoruesi = new Perdoruesi();
        perdoruesi.setEmail(request.getEmail());
        perdoruesi.setFjalekalimiHash(passwordEncoder.encode(request.getFjalekalimi()));
        perdoruesi.setRoliId(roleOpt.get().getId());
        perdoruesi.setEmri(request.getEmri());
        perdoruesi.setMbiemri(request.getMbiemri());
        perdoruesi.setTelefoni(request.getTelefoni());
        perdoruesi.setAdresa(request.getAdresa());
        perdoruesi.setDataLindjes(request.getDataLindjes());
        perdoruesi.setAktiv(true);
        perdoruesi.setEmailVerifikuar(false);

        Perdoruesi savedPerdoruesi = perdoruesiRepository.save(perdoruesi);

        String token = jwtUtil.generateToken(
            savedPerdoruesi.getId(),
            savedPerdoruesi.getEmail(),
            roleOpt.get().getEmri()
        );

        return new AuthResponse(
            token,
            savedPerdoruesi.getId(),
            savedPerdoruesi.getEmail(),
            savedPerdoruesi.getEmri(),
            savedPerdoruesi.getMbiemri(),
            roleOpt.get().getEmri()
        );
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        Optional<Perdoruesi> perdoruesiOpt = perdoruesiRepository.findByEmail(request.getEmail());

        if (perdoruesiOpt.isEmpty()) {
            return new AuthResponse("Email ose fjalëkalim i gabuar");
        }

        Perdoruesi perdoruesi = perdoruesiOpt.get();

        if (!perdoruesi.getAktiv()) {
            return new AuthResponse("Llogaria është e çaktivizuar");
        }

        if (!passwordEncoder.matches(request.getFjalekalimi(), perdoruesi.getFjalekalimiHash())) {
            return new AuthResponse("Email ose fjalëkalim i gabuar");
        }

        perdoruesiRepository.updateLastLogin(perdoruesi.getId());

        String token = jwtUtil.generateToken(
            perdoruesi.getId(),
            perdoruesi.getEmail(),
            perdoruesi.getRole().getEmri()
        );

        return new AuthResponse(
            token,
            perdoruesi.getId(),
            perdoruesi.getEmail(),
            perdoruesi.getEmri(),
            perdoruesi.getMbiemri(),
            perdoruesi.getRole().getEmri()
        );
    }

    public AuthResponse validateToken(String token) {
        try {
            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.extractEmail(token);
                Optional<Perdoruesi> perdoruesiOpt = perdoruesiRepository.findByEmail(email);

                if (perdoruesiOpt.isPresent()) {
                    Perdoruesi perdoruesi = perdoruesiOpt.get();
                    return new AuthResponse(
                        token,
                        perdoruesi.getId(),
                        perdoruesi.getEmail(),
                        perdoruesi.getEmri(),
                        perdoruesi.getMbiemri(),
                        perdoruesi.getRole().getEmri()
                    );
                }
            }
            return new AuthResponse("Token i pavlefshëm");
        } catch (Exception e) {
            return new AuthResponse("Token i pavlefshëm");
        }
    }

    public Optional<Perdoruesi> getPerdoruesiByEmail(String email) {
        return perdoruesiRepository.findByEmail(email);
    }
}
