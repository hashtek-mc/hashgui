package fr.hashtek.spigot.hashgui;

import fr.hashtek.spigot.hashgui.handler.click.ClickAction;
import fr.hashtek.spigot.hashgui.handler.click.ClickHandler;
import fr.hashtek.spigot.hashgui.manager.HashGuiManager;
import fr.hashtek.spigot.hashgui.page.HashPage;
import fr.hashtek.spigot.hashitem.HashItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PaginatedHashGui extends HashGui
{

    private final List<HashPage> pages;

    private int currentPageIndex;


    /**
     * Creates a new GUI.
     *
     * @param	title	GUI's title
     * @param	size	GUI's amount of lines (must be between 1 and 6).
     */
    public PaginatedHashGui(String title, int size)
    {
        super(title, size);

        this.pages = new ArrayList<HashPage>();
    }

    /**
     * Creates a new instance of HashGUI from an existing Inventory.
     *
     * @param	inventory	Inventory
     */
    public PaginatedHashGui(Inventory inventory)
    {
        super(inventory);

        this.pages = new ArrayList<HashPage>();
    }


    public void setPreviousPageItem(HashItem item, HashGuiManager guiManager)
    {
        item.addClickHandler(
            new ClickHandler()
                .addAllClickTypes()
                .setClickAction((Player player, HashGui gui, ItemStack i, int slot) -> {
                    if (!(gui instanceof PaginatedHashGui))
                        return; // FIXME: Maybe an error.

                    final PaginatedHashGui paginatedGui = (PaginatedHashGui) gui;
                    final int pageIndex = paginatedGui.getCurrentPageIndex() - 1;

                    if (pageIndex < 0)
                        return;

                    paginatedGui.setCurrentPageIndex(pageIndex);
                    paginatedGui.updatePage(player);
                })
        );

        item.build(guiManager);
    }

    public void setNextPageItem(HashItem item, HashGuiManager guiManager)
    {
        item.addClickHandler(
            new ClickHandler()
                .addAllClickTypes()
                .setClickAction((Player player, HashGui gui, ItemStack i, int slot) -> {
                    if (!(gui instanceof PaginatedHashGui))
                        return; // FIXME: Maybe an error.

                    final PaginatedHashGui paginatedGui = (PaginatedHashGui) gui;
                    final int pageIndex = paginatedGui.getCurrentPageIndex() + 1;

                    if (pageIndex > this.pages.size())
                        return;

                    paginatedGui.setCurrentPageIndex(pageIndex);

                    paginatedGui.updatePage(player);
                })
        );

        item.build(guiManager);
    }

    public void updatePage(Player player)
    {
        final HashPage currentPage = this.getCurrentPage();

        for (int slot : currentPage.getItems().keySet()) {
            final HashItem item = currentPage.getItem(slot);

            this.setItem(slot, item);
        }

        this.update(player);
    }

    public HashPage getCurrentPage()
    {
        return this.pages.get(this.currentPageIndex);
    }

    public int getCurrentPageIndex()
    {
        return this.currentPageIndex;
    }

    public void setCurrentPageIndex(int index)
    {
        this.currentPageIndex = index;
    }

}
