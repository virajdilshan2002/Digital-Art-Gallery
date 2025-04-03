package lk.viraj.backend.service;

import lk.viraj.backend.dto.CategoryDTO;
import lk.viraj.backend.dto.ItemDTO;
import lk.viraj.backend.dto.UserDTO;
import lk.viraj.backend.dto.other.ItemFormDataDTO;

import java.util.List;

public interface ItemService {
    int saveItem(ItemDTO itemDTO);

    ItemDTO convertToItemDTO(ItemFormDataDTO itemFormDataDTO, UserDTO userDTO, CategoryDTO categoryDTO, String path);

    List<ItemDTO> getAllItems();

    List<ItemDTO> getOwnedItemsByUser(UserDTO userDTO);

    boolean deleteItemById(String itemId);
}
