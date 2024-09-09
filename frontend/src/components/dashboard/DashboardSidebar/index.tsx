import Logo from '@assets/images/logo.svg';
import Accordion from '@components/common/Accordion';
import { routes } from '@router/path';
import { Link } from 'react-router-dom';
import LogoutButton from './LogoutButton';
import S from './style';

interface Option {
  text: string;
  isSelected: boolean;
  postId: string;
  dashboardId: string;
}

interface DashboardSidebarProps {
  options: Option[];
}

export default function DashboardSidebar({ options }: DashboardSidebarProps) {
  return (
    <S.Container>
      <Link to={routes.dashboard.list()}>
        <S.Logo
          src={Logo}
          alt="크루루 로고"
        />
      </Link>

      <S.Contents>
        <Accordion title={<Link to={routes.dashboard.list()}>공고</Link>}>
          {options.map(({ text, isSelected, postId, dashboardId }, index) => (
            // eslint-disable-next-line react/no-array-index-key
            <Accordion.ListItem key={index}>
              <S.LinkContainer isSelected={isSelected}>
                <Link to={routes.dashboard.post({ dashboardId, postId })}>{text}</Link>
              </S.LinkContainer>
            </Accordion.ListItem>
          ))}
        </Accordion>
      </S.Contents>

      <LogoutButton />
    </S.Container>
  );
}
