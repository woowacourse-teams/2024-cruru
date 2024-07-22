import { NAV_BAR_MENU } from '@constants/constants';
import SeperatorDot from '@assets/images/separatorDot.svg';

import S from './style';

interface NavBarProps {
  currentMenuKey: string;
}

export default function ProcessNavBar({ currentMenuKey }: NavBarProps) {
  return (
    <S.NavBar>
      <S.NavItemList>
        {Object.entries(NAV_BAR_MENU).map(([key, name], index) => (
          <>
            <S.NavItem key={key}>
              <S.NavButton
                type="button"
                isCurrent={key === currentMenuKey}
              >
                {name}
              </S.NavButton>
            </S.NavItem>

            {Object.keys(NAV_BAR_MENU).length > index + 1 && (
              <img
                alt="separator"
                src={SeperatorDot}
              />
            )}
          </>
        ))}
      </S.NavItemList>
    </S.NavBar>
  );
}
