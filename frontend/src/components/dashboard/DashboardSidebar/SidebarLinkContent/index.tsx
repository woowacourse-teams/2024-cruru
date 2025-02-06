import { PropsWithChildren, ReactNode } from 'react';
import Tooltip from '@components/_common/molecules/Tooltip';
import S from './style';

interface SidebarLinkWrapperProps extends PropsWithChildren {
  isSelected: boolean;
  isSidebarOpen: boolean;
  onClick?: () => void;
}

interface SidebarLinkContentProps extends SidebarLinkWrapperProps {
  icon: ReactNode;
  text: string;
}

export default function SidebarLinkContent({
  icon,
  text,
  isSelected,
  isSidebarOpen,
  onClick,
}: SidebarLinkContentProps) {
  return isSidebarOpen ? (
    <SidebarLinkWrapper
      isSelected={isSelected}
      isSidebarOpen={isSidebarOpen}
      onClick={onClick}
    >
      <S.IconContainer>{icon}</S.IconContainer>
      <S.SidebarItemText>{text}</S.SidebarItemText>
    </SidebarLinkWrapper>
  ) : (
    <Tooltip
      content={text}
      placement="right"
      distanceFromTarget={20}
    >
      <SidebarLinkWrapper
        isSelected={isSelected}
        isSidebarOpen={isSidebarOpen}
        onClick={onClick}
      >
        <S.IconContainer>{icon}</S.IconContainer>
      </SidebarLinkWrapper>
    </Tooltip>
  );
}

function SidebarLinkWrapper({ children, isSelected, isSidebarOpen, onClick }: SidebarLinkWrapperProps) {
  return (
    <S.SidebarItemLink
      isSelected={isSelected}
      isSidebarOpen={isSidebarOpen}
      onClick={onClick}
    >
      {children}
    </S.SidebarItemLink>
  );
}
