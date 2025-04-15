package lk.viraj.backend.controller;

import lk.viraj.backend.dto.ItemDTO;
import lk.viraj.backend.dto.ResponseDTO;
import lk.viraj.backend.service.ItemService;
import lk.viraj.backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payhere")
@CrossOrigin()
public class PayHereTransactionController {
    @Value("${payhere.client-id}")
    private String clientId;

    @Value("${payhere.secret}")
    private String secret;

    @Autowired
    private ItemService itemService;

    @GetMapping("/success")
    public ResponseEntity<ResponseDTO> handleSuccess() {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "Payment Canceled!", null));
    }

    @GetMapping("/cancel")
    public ResponseEntity<ResponseDTO> handleCancel() {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "Payment Canceled!", null));
    }

    @PostMapping("/notify")
    public ResponseEntity<String> handleNotification(@RequestParam Map<String, String> payload) {
        return ResponseEntity.ok("Received");
    }

    @GetMapping("/payment-details")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ResponseDTO> getPaymentDetails(@RequestParam(value = "id") String id) {
        ItemDTO item = itemService.getItemById(UUID.fromString(id));

        // 1. Fetch item/payment info from DB
        String merchantId = clientId;
        String orderId = UUID.randomUUID().toString();
        String amount = String.valueOf(item.getPrice()); // Your logic here
        String currency = "LKR";
        String merchantSecret = secret;

        String hash = generateHash(merchantId, orderId, Double.parseDouble(amount), currency, merchantSecret);

        Map<String, String> response = new HashMap<>();
        response.put("merchant_id", merchantId);
        response.put("order_id", orderId);
        response.put("items", item.getName());
        response.put("amount", amount);
        response.put("currency", currency);
        response.put("hash", hash);

        // Add other info (user, address etc.)
        response.put("first_name", item.getUser().getName());
        response.put("last_name", "Not Provided");
        response.put("email", item.getUser().getEmail());
        response.put("phone", item.getUser().getContact());
        response.put("address", item.getUser().getAddress());
        response.put("city", "Not Provided");
        response.put("country", "Sri Lanka");

        response.put("return_url", "http://localhost:63342/Digital-Art-Gallery/FrontEnd/success.html");
        response.put("cancel_url", "http://localhost:63342/Digital-Art-Gallery/FrontEnd/cancel.html");
        response.put("notify_url", "http://localhost:8080/api/v1/payhere/notify");

        return ResponseEntity.ok(new ResponseDTO(200, "Received", response));
    }

    public String generateHash(String merchantId, String orderId, double amountDouble, String currency, String merchantSecret) {
        // Format amount to 2 decimal places (e.g., "1200.00")
        String amount = String.format("%.2f", amountDouble);

        // Construct data string for hashing
        String dataToHash = merchantId + orderId + amount + currency + merchantSecret;

        // Debugging output (optional but helpful)
        System.out.println("Hash Input: " + dataToHash);

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
            String hash = String.format("%032x", new BigInteger(1, digest));
            System.out.println("Generated Hash: " + hash); // Optional: log the hash
            return hash;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }


}
