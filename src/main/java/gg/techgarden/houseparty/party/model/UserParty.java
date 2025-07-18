package gg.techgarden.houseparty.party.model;

import gg.techgarden.houseparty.party.persistence.entity.Invite;
import gg.techgarden.houseparty.party.persistence.entity.Party;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserParty {
    private Party party;
    private Invite invite;
}
