package sophisticated_wolves.item;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import sophisticated_wolves.core.SWConfiguration;
import sophisticated_wolves.core.SWTabs;
import sophisticated_wolves.entity.SophisticatedWolf;
import sophisticated_wolves.gui.DogTagScreen;

/**
 * Sophisticated Wolves
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemDogTag extends Item {

    public ItemDogTag() {
        super(new Item.Properties()
                .tab(SWTabs.TAB));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        if (SWConfiguration.NAME_TAG_FOR_ANY_PETS.get()) {
            if (entity instanceof TamableAnimal animal) {
                return setName(animal, stack, player);
            }
        } else if (entity instanceof SophisticatedWolf wolf) {
            return setName(wolf, stack, player);
        }
        return super.interactLivingEntity(stack, player, entity, hand);
    }

    private static InteractionResult setName(TamableAnimal pet, ItemStack stack, Player player) {
        if (pet.isTame() && pet.getOwnerUUID() != null && pet.getOwnerUUID().equals(player.getUUID())) {
            if (player.getLevel().isClientSide()) {
                Minecraft.getInstance().setScreen(new DogTagScreen(pet));
            } else {
                stack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

}
