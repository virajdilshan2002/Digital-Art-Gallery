package lk.viraj.backend.service;

import lk.viraj.backend.dto.CategoryDTO;
import lk.viraj.backend.dto.ItemDTO;
import lk.viraj.backend.dto.UserDTO;
import lk.viraj.backend.dto.other.ItemFormDataDTO;

import java.util.List;
import java.util.UUID;

public interface ItemService {
    int saveItem(ItemDTO itemDTO);

    ItemDTO convertToItemDTO(ItemFormDataDTO itemFormDataDTO, UserDTO userDTO, CategoryDTO categoryDTO, String path);

    List<ItemDTO> getAllItems();

    List<ItemDTO> getOwnedItemsByUser(UserDTO userDTO);

    boolean deleteItemById(String itemId);

    ItemDTO getItemById(UUID iid);

    ItemDTO setUpdatedDetails(ItemDTO itemDTO, ItemFormDataDTO itemFormDataDTO);
}
